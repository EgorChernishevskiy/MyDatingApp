package org.example.user_profile.services;

import lombok.RequiredArgsConstructor;
import org.example.user_profile.dto.requests.ProfileRequestDTO;
import org.example.user_profile.dto.responses.ProfileResponseDTO;
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

    @Override
    public ProfileResponseDTO createProfile(ProfileRequestDTO dto) {

        ProfileEntity profile = profileMapper.toEntity(dto);
        ProfileEntity savedProfile = profileRepository.save(profile);

        return profileMapper.toResponseDTO(savedProfile);
    }

    @Override
    public ProfileResponseDTO getProfileById(Long id) {

        ProfileEntity profile = profileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found with id: " + id));

        return profileMapper.toResponseDTO(profile);
    }

    @Override
    public List<ProfileResponseDTO> getAllProfiles() {

        return profileRepository.findAll().stream()
                .map(profileMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProfileResponseDTO patchProfile(Long id, ProfileRequestDTO dto) {

        ProfileEntity profile = profileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found with id: " + id));

        if (dto.getName() != null && !dto.getName().isBlank()) {
            profile.setName(dto.getName());
        }

        if (dto.getGender() != null) {
            profile.setGender(dto.getGender());
        }

        if (dto.getAbout() != null && !dto.getAbout().isBlank()) {
            profile.setAbout(dto.getAbout());
        }

        ProfileEntity updatedProfile = profileRepository.save(profile);
        return profileMapper.toResponseDTO(updatedProfile);
    }

    @Override
    public void deleteProfile(Long id) {
        ProfileEntity profile = profileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found with id: " + id));
        profileRepository.delete(profile);
    }
}
