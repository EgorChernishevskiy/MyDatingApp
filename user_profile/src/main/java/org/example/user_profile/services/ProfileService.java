package org.example.user_profile.services;

import org.example.user_profile.entities.ProfileEntity;

import java.util.List;

public interface ProfileService {

    ProfileEntity createProfile(ProfileEntity profile);

    ProfileEntity getProfileById(Long id);

    List<ProfileEntity> getAllProfiles();

    ProfileEntity updateProfile(Long id, ProfileEntity profile);

    void deleteProfile(Long id);
}
