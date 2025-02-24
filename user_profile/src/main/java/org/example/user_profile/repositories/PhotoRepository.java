package org.example.user_profile.repositories;

import org.example.user_profile.entities.PhotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PhotoRepository extends JpaRepository<PhotoEntity, Long> {
    List<PhotoEntity> findByProfileId(Long profileId);

    Optional<PhotoEntity> findByUrl(String url);
}
