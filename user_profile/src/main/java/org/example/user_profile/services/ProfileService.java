package org.example.user_profile.services;

import org.example.user_profile.entities.ProfileEntity;

import java.util.List;

public interface ProfileService {

    ProfileEntity createProfile(String name, Boolean gender, String about);

    ProfileEntity getProfileById(Long id);

    List<ProfileEntity> getAllProfiles();

    ProfileEntity updateProfile(Long id, String name, Boolean gender, String about);

    void deleteProfile(Long id);
}
