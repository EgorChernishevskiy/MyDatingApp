package org.example.user_profile.controllers;

import lombok.RequiredArgsConstructor;
import org.example.user_profile.dto.responses.ProfileResponseDTO;
import org.example.user_profile.services.DeckService;
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

