package org.example.user_profile.controllers;

import lombok.RequiredArgsConstructor;
import org.example.user_profile.dto.requests.PreferencesRequestDTO;
import org.example.user_profile.dto.requests.ProfileRequestDTO;
import org.example.user_profile.dto.responses.PreferencesResponseDTO;
import org.example.user_profile.dto.responses.ProfileResponseDTO;
import org.example.user_profile.services.PreferencesServiceImpl;
import org.example.user_profile.services.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;
    private final PreferencesServiceImpl preferencesService;

    @PostMapping
    public ResponseEntity<ProfileResponseDTO> createProfile(@RequestBody ProfileRequestDTO dto) {

        return new ResponseEntity<>(profileService.createProfile(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProfileResponseDTO>> getAllProfiles() {

        return new ResponseEntity<>(profileService.getAllProfiles(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileResponseDTO> getProfileById(@PathVariable Long id) {

        return new ResponseEntity<>(profileService.getProfileById(id), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProfileResponseDTO> updateProfile(@PathVariable Long id, @RequestBody ProfileRequestDTO dto) {

        return new ResponseEntity<>(profileService.patchProfile(id, dto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfileById(@PathVariable Long id) {

        profileService.deleteProfile(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/preferences/{id}")
    public ResponseEntity<PreferencesResponseDTO> getPreferenceById(@PathVariable Long id) {

        PreferencesResponseDTO response = preferencesService.getPreferenceById(id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/preferences/{id}")
    public ResponseEntity<PreferencesResponseDTO> addPreference(
            @PathVariable Long id,
            @RequestBody PreferencesRequestDTO preferencesRequestDTO
    ) {
        PreferencesResponseDTO response = preferencesService.addPreference(id, preferencesRequestDTO);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/preferences/{id}")
    public ResponseEntity<PreferencesResponseDTO> patchPreference(
            @PathVariable Long id,
            @RequestBody PreferencesRequestDTO preferencesRequestDTO
    ) {
        PreferencesResponseDTO response = preferencesService.patchPreference(id, preferencesRequestDTO);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
