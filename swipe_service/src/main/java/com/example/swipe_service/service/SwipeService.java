package com.example.swipe_service.service;

import com.example.swipe_service.dto.MatchResponseDTO;
import com.example.swipe_service.dto.SwipeRequestDTO;
import com.example.swipe_service.entity.SwipeEntity;
import com.example.swipe_service.kafka.MatchEvent;
import com.example.swipe_service.kafka.MatchEventProducer;
import com.example.swipe_service.repository.SwipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SwipeService {

    private final SwipeRepository swipeRepository;
    private final MatchEventProducer matchEventProducer;

    public MatchResponseDTO swipe(SwipeRequestDTO request) {
        // 1. Сначала проверяем - есть ли запись в БД, где этот пользователь свайпал другого
        Optional<SwipeEntity> directSwipe = swipeRepository.findByUserIdFromAndUserIdTo(
                request.getUserIdFrom(), request.getUserIdTo()
        );

        if (directSwipe.isPresent()) {
            SwipeEntity swipe = directSwipe.get();
            swipe.setDecisionFrom(request.getDecision());
            swipeRepository.save(swipe);
            return new MatchResponseDTO(request.getUserIdFrom(), request.getUserIdTo(), false);
        }

        // 2. Ищем — может другой пользователь уже свайпал этого
        Optional<SwipeEntity> reverseSwipe = swipeRepository.findByUserIdFromAndUserIdTo(
                request.getUserIdTo(), request.getUserIdFrom()
        );

        if (reverseSwipe.isPresent()) {
            SwipeEntity swipe = reverseSwipe.get();
            swipe.setDecisionTo(request.getDecision());
            swipeRepository.save(swipe);

            if (Boolean.TRUE.equals(swipe.getDecisionFrom()) && Boolean.TRUE.equals(swipe.getDecisionTo())) {
                matchEventProducer.sendMatchEvent(new MatchEvent(
                        request.getUserIdFrom(), request.getUserIdTo()
                ));
                return new MatchResponseDTO(request.getUserIdFrom(), request.getUserIdTo(), true);
            }

            return new MatchResponseDTO(request.getUserIdFrom(), request.getUserIdTo(), false);
        }

        // 3. Если записей нет — создаём новую
        SwipeEntity newSwipe = SwipeEntity.builder()
                .userIdFrom(request.getUserIdFrom())
                .userIdTo(request.getUserIdTo())
                .decisionFrom(request.getDecision())
                .build();

        swipeRepository.save(newSwipe);

        return new MatchResponseDTO(request.getUserIdFrom(), request.getUserIdTo(),false);
    }
}

