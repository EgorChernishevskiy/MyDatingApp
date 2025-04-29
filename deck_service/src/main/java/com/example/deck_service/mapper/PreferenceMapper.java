package com.example.deck_service.mapper;

import com.example.deck_service.dto.PreferencesRequestDTO;
import com.example.deck_service.dto.PreferencesResponseDTO;
import com.example.deck_service.entity.profile.PreferencesEntity;
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
