package com.example.deck_service.dto;

import com.example.deck_service.utils.enums.Gender;
import lombok.Builder;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class ProfileResponseDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private Integer age;
    private Gender gender;
    private String about;
    private GeoLocationDTO location;
    private List<String> photoUrls;
}