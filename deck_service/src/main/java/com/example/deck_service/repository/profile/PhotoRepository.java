package com.example.deck_service.repository.profile;

import com.example.deck_service.entity.profile.PhotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoRepository extends JpaRepository<PhotoEntity, Long> {

    List<PhotoEntity> findByProfileId(Long profileId);
}
