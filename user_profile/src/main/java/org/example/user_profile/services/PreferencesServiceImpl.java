package org.example.user_profile.services;

import lombok.RequiredArgsConstructor;
import org.example.user_profile.dto.requests.PreferencesRequestDTO;
import org.example.user_profile.dto.responses.PreferencesResponseDTO;
import org.example.user_profile.entities.PreferencesEntity;
import org.example.user_profile.entities.ProfileEntity;
import org.example.user_profile.exceptions.ResourceNotFoundException;
import org.example.user_profile.mappers.PreferenceMapper;
import org.example.user_profile.repositories.PreferenceRepository;
import org.example.user_profile.repositories.ProfileRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PreferencesServiceImpl implements PreferencesService {

    private final PreferenceRepository preferenceRepository;
    private final ProfileRepository profileRepository;
    private final PreferenceMapper preferenceMapper;

    @Override
    public PreferencesResponseDTO getPreferenceById(Long id) {

        PreferencesEntity preferencesEntity = preferenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Preferences not found with profile id: " + id));

        return preferenceMapper.toResponseDTO(preferencesEntity);
    }

    @Override
    public PreferencesResponseDTO addPreference(Long id, PreferencesRequestDTO dto) {

        PreferencesEntity preferencesEntity = preferenceMapper.toEntity(dto);

        ProfileEntity profile = profileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found with id: " + id));

        preferencesEntity.setProfile(profile);

        PreferencesEntity savedPreferences = preferenceRepository.save(preferencesEntity);

        return preferenceMapper.toResponseDTO(savedPreferences);
    }

    @Override
    public PreferencesResponseDTO patchPreference(Long id, PreferencesRequestDTO dto) {

        PreferencesEntity existingPreference = preferenceRepository.findByProfileId(id)
                .orElseThrow(() -> new RuntimeException("Preferences not found for user with ID " + id));

        if (dto.getGender() != null) {
            existingPreference.setGender(dto.getGender());
        }

        if (dto.getAgeMin() != null) {
            existingPreference.setAgeMin(dto.getAgeMin());
        }

        if (dto.getAgeMax() != null) {
            existingPreference.setAgeMax(dto.getAgeMax());
        }

        if (dto.getRadius() != null) {
            existingPreference.setRadius(dto.getRadius());
        }

        PreferencesEntity updatedPreference = preferenceRepository.save(existingPreference);

        return preferenceMapper.toResponseDTO(updatedPreference);
    }

}
