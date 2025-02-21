package org.example.user_profile.controllers;

import lombok.RequiredArgsConstructor;
import org.example.user_profile.dto.requests.PreferencesRequestDTO;
import org.example.user_profile.dto.responses.PreferencesResponseDTO;
import org.example.user_profile.services.PreferencesServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/preferences")
@RequiredArgsConstructor
public class PreferencesController {

    private final PreferencesServiceImpl preferencesService;

    @GetMapping("/{id}")
    public ResponseEntity<PreferencesResponseDTO> getPreferenceById(@PathVariable Long id) {

        PreferencesResponseDTO response = preferencesService.getPreferenceById(id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<PreferencesResponseDTO> addPreference(
            @PathVariable Long id,
            @RequestBody PreferencesRequestDTO preferencesRequestDTO
    ) {
        PreferencesResponseDTO response = preferencesService.addPreference(id, preferencesRequestDTO);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PreferencesResponseDTO> patchPreference(
            @PathVariable Long id,
            @RequestBody PreferencesRequestDTO preferencesRequestDTO
    ) {
        PreferencesResponseDTO response = preferencesService.patchPreference(id, preferencesRequestDTO);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
