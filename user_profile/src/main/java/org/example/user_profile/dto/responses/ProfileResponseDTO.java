package org.example.user_profile.dto.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfileResponseDTO {
    private Long id;
    private String name;
    private Boolean gender;
    private String about;
}