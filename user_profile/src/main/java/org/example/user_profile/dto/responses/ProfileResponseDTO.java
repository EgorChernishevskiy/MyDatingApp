package org.example.user_profile.dto.responses;

import lombok.Builder;
import lombok.Data;
import org.example.user_profile.utils.enums.Gender;

import java.util.List;

@Data
@Builder
public class ProfileResponseDTO {
    private Long id;
    private String name;
    private Integer age;
    private Gender gender;
    private String about;
    private List<String> photoUrls;
}