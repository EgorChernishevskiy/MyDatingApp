package com.example.deck_service.service;

import com.example.deck_service.dto.PreferencesResponseDTO;
import com.example.deck_service.entity.profile.PreferencesEntity;
import com.example.deck_service.exceptions.ResourceNotFoundException;
import com.example.deck_service.mapper.PreferenceMapper;
import com.example.deck_service.repository.profile.PreferenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PreferencesService {

    private final PreferenceRepository preferenceRepository;
    private final PreferenceMapper preferenceMapper;

    public PreferencesResponseDTO getPreferenceById(Long id) {

        PreferencesEntity preferencesEntity = preferenceRepository.findByProfileId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Preferences not found for user with ID " + id));

        return preferenceMapper.toResponseDTO(preferencesEntity);
    }
}
