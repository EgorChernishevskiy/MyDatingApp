package com.example.deck_service.repository.swipe;

import com.example.deck_service.entity.swipe.SwipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SwipeRepository extends JpaRepository<SwipeEntity, Long> {

    List<SwipeEntity> findAllByUserIdFrom(Long userIdFrom);
}