package org.example.user_profile.mappers;

import org.example.user_profile.dto.requests.ProfileRequestDTO;
import org.example.user_profile.dto.responses.ProfileResponseDTO;
import org.example.user_profile.entities.ProfileEntity;
import org.springframework.stereotype.Component;

@Component
public class ProfileMapper {

    public ProfileEntity toEntity(ProfileRequestDTO dto) {
        return ProfileEntity.builder()
                .name(dto.getName())
                .gender(dto.getGender())
                .about(dto.getAbout())
                .build();
    }

    public ProfileResponseDTO toResponseDTO(ProfileEntity entity) {
        return ProfileResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .gender(entity.getGender())
                .about(entity.getAbout())
                .build();
    }
}