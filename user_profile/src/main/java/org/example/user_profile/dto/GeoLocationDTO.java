package org.example.user_profile.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GeoLocationDTO {
    private double latitude;
    private double longitude;
}