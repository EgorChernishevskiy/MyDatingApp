package com.example.deck_service.dto;

import com.example.deck_service.utils.enums.Gender;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfileRequestDTO {
    private String name;
    private Integer age;
    private Gender gender;
    private String about;
    private Double latitude;
    private Double longitude;
}
