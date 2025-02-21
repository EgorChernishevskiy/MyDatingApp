package org.example.user_profile.dto.responses;

import lombok.Builder;
import lombok.Data;
import org.example.user_profile.utils.enums.Gender;

@Data
@Builder
public class PreferencesResponseDTO {
    private Long id;
    private Gender gender;
    private Integer ageMin;
    private Integer ageMax;
    private Integer radius;
}