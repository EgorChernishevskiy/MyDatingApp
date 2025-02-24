package org.example.user_profile.services;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.example.user_profile.entities.PhotoEntity;
import org.example.user_profile.repositories.PhotoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
public class PhotoServiceImpl implements PhotoService {

    private final AmazonS3 s3Client;
    private final PhotoRepository photoRepository;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    // Конструктор для инициализации S3-клиента (для Amazon S3 или Yandex Cloud, если понадобится изменить endpoint)
    public PhotoServiceImpl(@Value("${aws.accessKeyId}") String accessKeyId,
                            @Value("${aws.secretKey}") String secretKey,
                            @Value("${aws.region}") String region,
                            PhotoRepository photoRepository) {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKeyId, secretKey);
        this.s3Client = AmazonS3ClientBuilder.standard()
                // Если нужно использовать Yandex Cloud, можно добавить:
                // .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("https://storage.yandexcloud.net", region))
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();
        this.photoRepository = photoRepository;
    }

    @Override
    public String uploadPhoto(MultipartFile file) {

        String key = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            s3Client.putObject(new PutObjectRequest(bucketName, key, file.getInputStream(), metadata));
        } catch (AmazonServiceException | IOException e) {
            log.error("Error uploading file to S3: {}", e.getMessage());
            throw new RuntimeException("Failed to upload file", e);
        }

        return s3Client.getUrl(bucketName, key).toString();
    }

    @Override
    public List<String> uploadPhotos(List<MultipartFile> files) {
        return files.stream()
                .map(this::uploadPhoto)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getPhotoUrlsByProfileId(Long profileId) {

        List<PhotoEntity> photos = photoRepository.findByProfileId(profileId);

        return photos.stream()
                .map(PhotoEntity::getUrl)
                .collect(Collectors.toList());
    }

    @Override
    public void deletePhotosByProfileId(Long profileId) {
        List<PhotoEntity> photos = photoRepository.findByProfileId(profileId);
        for (PhotoEntity photo : photos) {
            String url = photo.getUrl();
            String key = extractKeyFromUrl(url);
            try {
                s3Client.deleteObject(bucketName, key);
            } catch (AmazonServiceException e) {
                log.error("Error deleting file from S3: {}", e.getMessage());
            }
        }
        photoRepository.deleteAll(photos);
    }

    @Override
    public void deletePhoto(String url) {
        // Определите ключ на основании URL. Здесь нужен обратный механизм.
        // Допустим, вы сохраняете ключ вместе с URL или умеете его извлекать из URL.
        String key = extractKeyFromUrl(url);

        try {
            s3Client.deleteObject(bucketName, key);
        } catch (AmazonServiceException e) {
            log.error("Error deleting file from S3: {}", e.getMessage());
            throw new RuntimeException("Failed to delete file", e);
        }
    }

    private String extractKeyFromUrl(String url) {
        // Реализуйте извлечение ключа из URL. Например, если URL имеет формат
        // https://bucket-name.s3.amazonaws.com/key, можно вернуть часть после последнего "/".
        return url.substring(url.lastIndexOf("/") + 1);
    }
}
