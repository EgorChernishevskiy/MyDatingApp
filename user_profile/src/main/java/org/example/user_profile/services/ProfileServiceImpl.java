package org.example.user_profile.services;

import lombok.RequiredArgsConstructor;
import org.example.user_profile.exceptions.BadRequestException;
import org.example.user_profile.exceptions.ResourceNotFoundException;
import org.example.user_profile.entities.ProfileEntity;
import org.example.user_profile.repositories.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;

    @Override
    public ProfileEntity createProfile(String name, Boolean gender, String about) {

        ProfileEntity newProfile = ProfileEntity.builder()
                .name(name)
                .gender(gender)
                .about(about)
                .build();

        return profileRepository.save(newProfile);
    }

    @Override
    public ProfileEntity getProfileById(Long id) {
        return profileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found with id: " + id));
    }

    @Override
    public List<ProfileEntity> getAllProfiles() {
        return profileRepository.findAll();
    }

    @Override
    public ProfileEntity updateProfile(Long id, String name, Boolean gender, String about) {

        ProfileEntity existingProfile = getProfileById(id);

        if (name != null && !name.isBlank()) {
            existingProfile.setName(name);
        }

        if (gender != null) {
            existingProfile.setGender(gender);
        }

        if (about != null && !about.isBlank()) {
            existingProfile.setAbout(about);
        }

        return profileRepository.save(existingProfile);
    }

    @Override
    public void deleteProfile(Long id) {
        ProfileEntity profile = getProfileById(id);
        profileRepository.delete(profile);
    }
}
