package com.example.deck_service.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.example.deck_service.entity.profile.PhotoEntity;
import com.example.deck_service.repository.profile.PhotoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
public class PhotoService {

    private final PhotoRepository photoRepository;
    
    public PhotoService(
            @Value("${aws.accessKeyId}") String accessKeyId,
            @Value("${aws.secretKey}") String secretKey,
            @Value("${aws.region}") String region,
            @Value("${aws.s3.endpoint:}") String endpoint,
            PhotoRepository photoRepository)
    {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKeyId, secretKey);
        AmazonS3ClientBuilder builder = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds));
        if (endpoint != null && !endpoint.isBlank()) {
            builder.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, region))
                    .withPathStyleAccessEnabled(true);
        } else {
            builder.withRegion(region);
        }
        this.photoRepository = photoRepository;
    }

    public List<String> getPhotoUrlsByProfileId(Long profileId) {

        List<PhotoEntity> photos = photoRepository.findByProfileId(profileId);

        return photos.stream()
                .map(PhotoEntity::getUrl)
                .collect(Collectors.toList());
    }

}
