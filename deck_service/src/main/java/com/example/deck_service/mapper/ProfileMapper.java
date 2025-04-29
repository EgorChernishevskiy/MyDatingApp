package com.example.deck_service.mapper;

import com.example.deck_service.dto.ProfileRequestDTO;
import com.example.deck_service.dto.ProfileResponseDTO;
import com.example.deck_service.entity.profile.ProfileEntity;
import org.springframework.stereotype.Component;

@Component
public class ProfileMapper {

    public ProfileEntity toEntity(ProfileRequestDTO dto) {
        return ProfileEntity.builder()
                .name(dto.getName())
                .age(dto.getAge())
                .gender(dto.getGender())
                .about(dto.getAbout())
                .build();
    }

    public ProfileResponseDTO toResponseDTO(ProfileEntity entity) {
        return ProfileResponseDTO.builder()
                .id(entity.getId())
                .age(entity.getAge())
                .name(entity.getName())
                .gender(entity.getGender())
                .about(entity.getAbout())
                .build();
    }
}