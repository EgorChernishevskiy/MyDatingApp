package com.example.swipe_service.repository;

import com.example.swipe_service.entity.SwipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SwipeRepository extends JpaRepository<SwipeEntity, Long> {

    Optional<SwipeEntity> findByUserIdFromAndUserIdTo(Long userIdFrom, Long userIdTo);

    Optional<SwipeEntity> findByUserIdToAndUserIdFrom(Long userIdTo, Long userIdFrom);
}