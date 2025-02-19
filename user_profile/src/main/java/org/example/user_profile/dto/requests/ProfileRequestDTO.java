package org.example.user_profile.dto.requests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfileRequestDTO {
    private String name;
    private Boolean gender;
    private String about;
}
