package com.example.deck_service.dto;

import com.example.deck_service.utils.enums.Gender;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PreferencesRequestDTO {
    private Gender gender;
    private Integer ageMin;
    private Integer ageMax;
    private Integer radius;
}
