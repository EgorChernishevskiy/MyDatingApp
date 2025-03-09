package org.example.user_profile.dto.responses;

import lombok.Builder;
import lombok.Data;
import org.example.user_profile.utils.enums.Gender;

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
    private List<String> photoUrls;
}