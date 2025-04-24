package org.example.user_profile.repositories;

import org.example.user_profile.entities.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<LocationEntity, Long> {
    Optional<LocationEntity> findByProfileId(Long profileId);
    void deleteAllByProfileId(Long profileId);
}
