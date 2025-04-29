package com.example.deck_service.controller;

import com.example.deck_service.dto.ProfileResponseDTO;
import com.example.deck_service.service.DeckService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deck")
@RequiredArgsConstructor
public class DeckController {

    private final DeckService deckService;

    @GetMapping("/{userId}")
    public List<ProfileResponseDTO> getDeck(@PathVariable Long userId) {
        return deckService.getDeck(userId);
    }

    @PostMapping("/{userId}/generate")
    public List<ProfileResponseDTO> generateDeck(@PathVariable Long userId) {
        return deckService.generateDeck(userId);
    }
}

