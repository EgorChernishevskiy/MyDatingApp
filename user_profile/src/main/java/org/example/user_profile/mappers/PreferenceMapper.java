package org.example.user_profile.mappers;

import org.example.user_profile.dto.requests.PreferencesRequestDTO;
import org.example.user_profile.dto.responses.PreferencesResponseDTO;
import org.example.user_profile.entities.PreferencesEntity;
import org.springframework.stereotype.Component;

@Component
public class PreferenceMapper {

    public PreferencesEntity toEntity(PreferencesRequestDTO dto) {
        return PreferencesEntity.builder()
                .gender(dto.getGender())
                .ageMin(dto.getAgeMin())
                .ageMax(dto.getAgeMax())
                .radius(dto.getRadius())
                .build();
    }

    public PreferencesResponseDTO toResponseDTO(PreferencesEntity entity) {
        return PreferencesResponseDTO.builder()
                .id(entity.getId())
                .gender(entity.getGender())
                .ageMax(entity.getAgeMax())
                .ageMin(entity.getAgeMin())
                .radius(entity.getRadius())
                .build();

    }
}
