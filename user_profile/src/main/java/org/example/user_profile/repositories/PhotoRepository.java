package org.example.user_profile.repositories;

import org.example.user_profile.entities.PhotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoRepository extends JpaRepository<PhotoEntity, Long> {
    List<PhotoEntity> findByProfileId(Long profileId);
}
