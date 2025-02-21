package org.example.user_profile.services;

import org.example.user_profile.dto.requests.PreferencesRequestDTO;
import org.example.user_profile.dto.responses.PreferencesResponseDTO;

public interface PreferencesService {

    PreferencesResponseDTO getPreferenceById(Long id);

    PreferencesResponseDTO addPreference(Long id, PreferencesRequestDTO dto);

    PreferencesResponseDTO patchPreference(Long id, PreferencesRequestDTO dto);
}

