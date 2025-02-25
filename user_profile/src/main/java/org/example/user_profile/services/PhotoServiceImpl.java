package org.example.user_profile.services;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.example.user_profile.entities.PhotoEntity;
import org.example.user_profile.entities.ProfileEntity;
import org.example.user_profile.exceptions.ResourceNotFoundException;
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

    @PersistenceContext
    private EntityManager entityManager;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    // Обратите внимание на дополнительный параметр endpoint, который может быть пустым.
    public PhotoServiceImpl(
            @Value("${aws.accessKeyId}") String accessKeyId,
            @Value("${aws.secretKey}") String secretKey,
            @Value("${aws.region}") String region,
            @Value("${aws.s3.endpoint:}") String endpoint,  // Если не задан – пустая строка
            PhotoRepository photoRepository)
    {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKeyId, secretKey);
        AmazonS3ClientBuilder builder = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds));
        if (endpoint != null && !endpoint.isBlank()) {
            // Если указан endpoint (например, для Yandex Cloud), используем его вместе с регионом
            builder.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, region))
                    .withPathStyleAccessEnabled(true);
        } else {
            // Иначе просто задаем регион
            builder.withRegion(region);
        }
        this.s3Client = builder.build();
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
    public void addPhotosToProfile(Long profileId, List<MultipartFile> files) {
        // Получаем ссылку (proxy) на существующий профиль без загрузки всей сущности
        ProfileEntity profile = entityManager.getReference(ProfileEntity.class, profileId);
        List<String> photoUrls = uploadPhotos(files);
        List<PhotoEntity> photoEntities = photoUrls.stream()
                .map(url -> PhotoEntity.builder()
                        .url(url)
                        .profile(profile)
                        .build())
                .collect(Collectors.toList());
        photoRepository.saveAll(photoEntities);
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
    public void deletePhotoByURl(String url) {

        PhotoEntity photo = photoRepository.findByUrl(url)
                .orElseThrow(() -> new ResourceNotFoundException("Photo not found with url: " + url));

        String key = extractKeyFromUrl(photo.getUrl());

        try {
            s3Client.deleteObject(bucketName, key);
        } catch (AmazonServiceException e) {
            log.error("Error deleting file from S3: {}", e.getMessage());
            throw new RuntimeException("Failed to delete file", e);
        }
        photoRepository.delete(photo);
    }

    private String extractKeyFromUrl(String url) {
        // Реализуйте извлечение ключа из URL. Например, если URL имеет формат
        // https://bucket-name.s3.amazonaws.com/key, можно вернуть часть после последнего "/".
        return url.substring(url.lastIndexOf("/") + 1);
    }
}
