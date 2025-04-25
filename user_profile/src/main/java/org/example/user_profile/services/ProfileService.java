package org.example.user_profile.services;

import lombok.RequiredArgsConstructor;
import org.example.user_profile.dto.GeoLocationDTO;
import org.example.user_profile.dto.requests.ProfileRequestDTO;
import org.example.user_profile.dto.responses.ProfileResponseDTO;
import org.example.user_profile.exceptions.BadRequestException;
import org.example.user_profile.exceptions.ResourceNotFoundException;
import org.example.user_profile.entities.ProfileEntity;
import org.example.user_profile.mappers.ProfileMapper;
import org.example.user_profile.repositories.ProfileRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final GeoLocationService geoLocationService;
    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;
    private final PhotoService photoService;

    @CachePut(value = "profiles", key = "#result.id")
    public ProfileResponseDTO createProfile(ProfileRequestDTO dto) {

        if (dto.getName() == null || dto.getName().isBlank()) {
            throw new BadRequestException("Name cannot be blank");
        }

        if (dto.getAge() == null) {
            throw new BadRequestException("Age cannot be null");
        }

        if (dto.getGender() == null) {
            throw new BadRequestException("Gender cannot be null");
        }

        if (dto.getAbout() == null || dto.getAbout().isBlank()) {
            throw new BadRequestException("About cannot be blank");
        }

        if (dto.getLatitude() == null || dto.getLongitude() == null) {
            throw new BadRequestException("Latitude and Longitude cannot be blank");
        }

        ProfileEntity profile = profileMapper.toEntity(dto);
        ProfileEntity savedProfile = profileRepository.save(profile);

        geoLocationService.setLocation(savedProfile.getId(), dto.getLatitude(), dto.getLongitude());

        GeoLocationDTO location = geoLocationService.getLocation(savedProfile.getId());

        ProfileResponseDTO responseDTO = profileMapper.toResponseDTO(savedProfile);
        responseDTO.setLocation(location);

        return responseDTO;
    }

    @Cacheable(value = "profiles", key = "#id")
    public ProfileResponseDTO getProfileById(Long id) {

        ProfileEntity profile = profileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found with id: " + id));

        List<String> photoUrls = photoService.getPhotoUrlsByProfileId(profile.getId());

        ProfileResponseDTO responseDTO = profileMapper.toResponseDTO(profile);
        responseDTO.setPhotoUrls(photoUrls);

        GeoLocationDTO location = geoLocationService.getLocation(profile.getId());
        responseDTO.setLocation(location);

        return responseDTO;
    }

    public Page<ProfileResponseDTO> getAllProfiles(Pageable pageable) {
        return profileRepository.findAll(pageable)
                .map(profile -> {
                    List<String> photoUrls = photoService.getPhotoUrlsByProfileId(profile.getId());
                    GeoLocationDTO location = geoLocationService.getLocation(profile.getId());

                    ProfileResponseDTO dto = profileMapper.toResponseDTO(profile);
                    dto.setPhotoUrls(photoUrls);
                    dto.setLocation(location);

                    return dto;
                });
    }

    @CachePut(value = "profiles", key = "#id")
    public ProfileResponseDTO patchProfile(Long id, ProfileRequestDTO dto) {

        ProfileEntity profile = profileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found with id: " + id));

        if (dto.getName() != null && !dto.getName().isBlank()) {
            profile.setName(dto.getName());
        }

        if (dto.getAge() != null) {
            profile.setAge(dto.getAge());
        }

        if (dto.getGender() != null) {
            profile.setGender(dto.getGender());
        }

        if (dto.getAbout() != null && !dto.getAbout().isBlank()) {
            profile.setAbout(dto.getAbout());
        }

        ProfileEntity updatedProfile = profileRepository.save(profile);

        if (dto.getLatitude() != null && dto.getLongitude() != null) {
            geoLocationService.setLocation(updatedProfile.getId(), dto.getLatitude(), dto.getLongitude());
        }

        List<String> photoUrls = photoService.getPhotoUrlsByProfileId(updatedProfile.getId());

        GeoLocationDTO location = geoLocationService.getLocation(profile.getId());

        ProfileResponseDTO responseDTO = profileMapper.toResponseDTO(updatedProfile);
        responseDTO.setPhotoUrls(photoUrls);
        responseDTO.setLocation(location);

        return responseDTO;
    }

    @CacheEvict(value = "profiles", key = "#id")
    @Transactional
    public void deleteProfile(Long id) {

        ProfileEntity profile = profileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found with id: " + id));

        photoService.deletePhotosByProfileId(profile.getId());
        geoLocationService.deleteLocation(id);
        profileRepository.delete(profile);
    }
}
