package org.example.user_profile.services;

import org.example.user_profile.dto.requests.ProfileRequestDTO;
import org.example.user_profile.dto.responses.ProfileResponseDTO;

import java.util.List;

public interface ProfileService {

    ProfileResponseDTO createProfile(ProfileRequestDTO profileRequestDTO);

    ProfileResponseDTO getProfileById(Long id);

    List<ProfileResponseDTO> getAllProfiles();

    ProfileResponseDTO patchProfile(Long id, ProfileRequestDTO profileRequestDTO);

    void deleteProfile(Long id);
}
