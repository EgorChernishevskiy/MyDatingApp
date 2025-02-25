package org.example.user_profile.services;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PhotoService {

    String uploadPhoto(MultipartFile file);

    List<String> uploadPhotos(List<MultipartFile> files);

    void addPhotosToProfile(Long profileId, List<MultipartFile> files);

    List<String> getPhotoUrlsByProfileId(Long profileId);

    void deletePhotosByProfileId(Long profileId);

    void deletePhotoByURl(String url);
}
