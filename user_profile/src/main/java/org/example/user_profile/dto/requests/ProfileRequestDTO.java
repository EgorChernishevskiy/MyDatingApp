package org.example.user_profile.dto.requests;

import lombok.Builder;
import lombok.Data;
import org.example.user_profile.utils.enums.Gender;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
