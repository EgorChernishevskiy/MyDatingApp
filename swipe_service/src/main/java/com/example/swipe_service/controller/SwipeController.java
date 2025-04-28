package com.example.swipe_service.controller;

import com.example.swipe_service.dto.MatchResponseDTO;
import com.example.swipe_service.dto.SwipeRequestDTO;
import com.example.swipe_service.service.SwipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/swipes")
@RequiredArgsConstructor
public class SwipeController {

    private final SwipeService swipeService;

    @PostMapping
    public ResponseEntity<MatchResponseDTO> swipe(@RequestBody SwipeRequestDTO request) {
        MatchResponseDTO match = swipeService.swipe(request);
        return ResponseEntity.ok(match);
    }
}
