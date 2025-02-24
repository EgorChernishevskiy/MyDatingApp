package org.example.user_profile.services;

import lombok.RequiredArgsConstructor;
import org.example.user_profile.dto.requests.ProfileRequestDTO;
import org.example.user_profile.dto.responses.ProfileResponseDTO;
import org.example.user_profile.entities.PhotoEntity;
import org.example.user_profile.exceptions.BadRequestException;
import org.example.user_profile.exceptions.ResourceNotFoundException;
import org.example.user_profile.entities.ProfileEntity;
import org.example.user_profile.mappers.ProfileMapper;
import org.example.user_profile.repositories.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;
    private final PhotoService photoService;

    @Override
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

        ProfileEntity profile = profileMapper.toEntity(dto);
        ProfileEntity savedProfile = profileRepository.save(profile);

        return profileMapper.toResponseDTO(savedProfile);
    }

    @Override
    public ProfileResponseDTO getProfileById(Long id) {

        ProfileEntity profile = profileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found with id: " + id));

        List<String> photoUrls = photoService.getPhotoUrlsByProfileId(profile.getId());

        ProfileResponseDTO responseDTO = profileMapper.toResponseDTO(profile);
        responseDTO.setPhotoUrls(photoUrls);

        return responseDTO;
    }

    @Override
    public List<ProfileResponseDTO> getAllProfiles() {

        return profileRepository.findAll().stream().map(profile -> {
            List<String> photoUrls = photoService.getPhotoUrlsByProfileId(profile.getId());
            ProfileResponseDTO dto = profileMapper.toResponseDTO(profile);
            dto.setPhotoUrls(photoUrls);
            return dto;                     //?????????????????
        }).collect(Collectors.toList());
    }

    @Override
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

        List<String> photoUrls = photoService.getPhotoUrlsByProfileId(updatedProfile.getId());

        ProfileResponseDTO responseDTO = profileMapper.toResponseDTO(updatedProfile);
        responseDTO.setPhotoUrls(photoUrls);

        return responseDTO;
    }

    @Override
    public void deleteProfile(Long id) {

        ProfileEntity profile = profileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found with id: " + id));

        photoService.deletePhotosByProfileId(profile.getId());
        profileRepository.delete(profile);
    }
}
