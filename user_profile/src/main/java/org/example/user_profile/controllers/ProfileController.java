package org.example.user_profile.controllers;

import lombok.RequiredArgsConstructor;
import org.example.user_profile.entities.ProfileEntity;
import org.example.user_profile.exceptions.BadRequestException;
import org.example.user_profile.services.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping
    public ResponseEntity<ProfileEntity> createProfile(
            @RequestParam("name") String name,
            @RequestParam("gender") Boolean gender,
            @RequestParam("about_me") String about
            )
    {
        if (name.isBlank()) {
            throw new BadRequestException("Name cannot be blank");
        }

        if (about.isBlank()) {
            throw new BadRequestException("About cannot be blank");
        }

        ProfileEntity createdProfile = profileService.createProfile(name, gender, about);

        return new ResponseEntity<>(createdProfile, HttpStatus.CREATED);
    }


}
