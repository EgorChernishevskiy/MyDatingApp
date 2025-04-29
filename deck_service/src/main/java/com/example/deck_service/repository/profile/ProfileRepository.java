package com.example.deck_service.repository.profile;

import com.example.deck_service.entity.profile.ProfileEntity;
import com.example.deck_service.utils.enums.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileRepository extends JpaRepository<ProfileEntity, Long> {

    @Query(value = """
    SELECT p.*
    FROM profile p
    JOIN location l ON l.user_id = p.id
    WHERE p.id != :userId
      AND p.gender = :gender
      AND p.age BETWEEN :ageMin AND :ageMax
      AND ST_DWithin(
          l.coordinates,
          (SELECT coordinates FROM location WHERE user_id = :userId),
          :radius
      )
    """, nativeQuery = true)
    List<ProfileEntity> findMatchesByPreferences(@Param("userId") Long userId,
                                                 @Param("gender") Gender gender,
                                                 @Param("ageMin") Integer ageMin,
                                                 @Param("ageMax") Integer ageMax,
                                                 @Param("radius") double radius);

    @Query("SELECT p.id FROM ProfileEntity p")
    List<Long> findAllIds();
}
