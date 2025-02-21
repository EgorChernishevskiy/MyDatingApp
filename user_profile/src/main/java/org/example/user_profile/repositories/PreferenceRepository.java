package org.example.user_profile.repositories;

import org.example.user_profile.entities.PreferencesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PreferenceRepository extends JpaRepository<PreferencesEntity, Long> {

    Optional<PreferencesEntity> findByProfileId(Long userId);
}
