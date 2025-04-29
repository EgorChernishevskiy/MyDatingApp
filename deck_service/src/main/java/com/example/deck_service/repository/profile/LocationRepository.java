package com.example.deck_service.repository.profile;

import com.example.deck_service.entity.profile.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<LocationEntity, Long> {

    Optional<LocationEntity> findByProfileId(Long profileId);
}
