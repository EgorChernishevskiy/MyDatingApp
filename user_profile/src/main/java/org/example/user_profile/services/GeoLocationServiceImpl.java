package org.example.user_profile.services;

import lombok.RequiredArgsConstructor;
import org.example.user_profile.dto.GeoLocationDTO;
import org.example.user_profile.entities.LocationEntity;
import org.example.user_profile.entities.ProfileEntity;
import org.example.user_profile.exceptions.ResourceNotFoundException;
import org.example.user_profile.repositories.LocationRepository;
import org.example.user_profile.repositories.ProfileRepository;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GeoLocationServiceImpl implements GeoLocationService {

    private final LocationRepository locationRepository;
    private final ProfileRepository profileRepository;
    private final GeometryFactory geometryFactory = new GeometryFactory();

    @Override
    @CacheEvict(value = "profiles", key = "#profileId")
    public void setLocation(Long profileId, double latitude, double longitude) {

        ProfileEntity profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found"));

        Point point = geometryFactory.createPoint(new Coordinate(longitude, latitude));
        point.setSRID(4326);

        LocationEntity location = locationRepository.findByProfileId(profileId)
                .orElse(LocationEntity.builder().profile(profile).build());

        location.setCoordinates(point);
        locationRepository.save(location);
    }

    @Override
    public void deleteLocation(Long profileId) {

        locationRepository.deleteAllByProfileId(profileId);
    }

    @Override
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
