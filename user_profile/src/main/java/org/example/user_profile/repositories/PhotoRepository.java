package org.example.user_profile.repositories;

import org.example.user_profile.entities.PhotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<PhotoEntity, Long> {
}
