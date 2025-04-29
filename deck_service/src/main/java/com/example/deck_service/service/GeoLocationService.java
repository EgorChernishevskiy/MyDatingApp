package com.example.deck_service.service;

import com.example.deck_service.dto.GeoLocationDTO;
import com.example.deck_service.entity.profile.LocationEntity;
import com.example.deck_service.exceptions.ResourceNotFoundException;
import com.example.deck_service.repository.profile.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GeoLocationService {

    private final LocationRepository locationRepository;

    public GeoLocationDTO getLocation(Long profileId) {

        LocationEntity location = locationRepository.findByProfileId(profileId)
                .orElseThrow(() -> new ResourceNotFoundException("Location not found"));

        Point point = location.getCoordinates();

        return GeoLocationDTO.builder()
                .latitude(point.getY())
                .longitude(point.getX())
                .build();
    }
}
