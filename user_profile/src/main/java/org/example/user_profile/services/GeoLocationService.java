package org.example.user_profile.services;

import org.example.user_profile.dto.GeoLocationDTO;

public interface GeoLocationService {
    void setLocation(Long profileId, double latitude, double longitude);
    GeoLocationDTO getLocation(Long profileId);
}
