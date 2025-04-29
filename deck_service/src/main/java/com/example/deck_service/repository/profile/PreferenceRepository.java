package com.example.deck_service.repository.profile;

import com.example.deck_service.entity.profile.PreferencesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PreferenceRepository extends JpaRepository<PreferencesEntity, Long> {

    Optional<PreferencesEntity> findByProfileId(Long userId);
}
