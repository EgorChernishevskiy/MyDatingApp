package org.example.user_profile.services;

import lombok.RequiredArgsConstructor;
import org.example.user_profile.ResourceNotFoundException;
import org.example.user_profile.entities.ProfileEntity;
import org.example.user_profile.repositories.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;

    @Override
    public ProfileEntity createProfile(ProfileEntity profile) {
        return profileRepository.save(profile);
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
    public ProfileEntity updateProfile(Long id, ProfileEntity profileDetails) {
        ProfileEntity existingProfile = getProfileById(id);
        existingProfile.setName(profileDetails.getName());
        existingProfile.setGender(profileDetails.getGender());
        existingProfile.setAbout(profileDetails.getAbout());
        return profileRepository.save(existingProfile);
    }

    @Override
    public void deleteProfile(Long id) {
        ProfileEntity profile = getProfileById(id);
        profileRepository.delete(profile);
    }
}
