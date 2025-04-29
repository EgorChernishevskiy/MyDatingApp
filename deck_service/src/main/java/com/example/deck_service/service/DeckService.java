package com.example.deck_service.service;

import com.example.deck_service.dto.PreferencesResponseDTO;
import com.example.deck_service.dto.ProfileResponseDTO;
import com.example.deck_service.entity.profile.ProfileEntity;
import com.example.deck_service.entity.swipe.SwipeEntity;
import com.example.deck_service.mapper.ProfileMapper;
import com.example.deck_service.repository.profile.ProfileRepository;
import com.example.deck_service.repository.swipe.SwipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeckService {

    private final ProfileRepository profileRepository;
    private final PreferencesService preferencesService;
    private final GeoLocationService geoLocationService;
    private final PhotoService photoService;
    private final ProfileMapper profileMapper;
    private final SwipeRepository swipeRepository;


    @CachePut(value = "decks", key = "#userId")
    public List<ProfileResponseDTO> generateDeck(Long userId) {

        PreferencesResponseDTO preferences = preferencesService.getPreferenceById(userId);

        List<ProfileEntity> candidates = profileRepository.findMatchesByPreferences(
                userId,
                preferences.getGender(),
                preferences.getAgeMin(),
                preferences.getAgeMax(),
                preferences.getRadius()
        );

        List<Long> swipedIds = swipeRepository.findAllByUserIdFrom(userId).stream()
                .map(SwipeEntity::getUserIdTo)
                .toList();

        List<ProfileEntity> filteredCandidates = candidates.stream()
                .filter(profile -> !swipedIds.contains(profile.getId()))
                .toList();

        return filteredCandidates.stream()
                .map(profile -> {
                    ProfileResponseDTO dto = profileMapper.toResponseDTO(profile);
                    dto.setPhotoUrls(photoService.getPhotoUrlsByProfileId(profile.getId()));
                    dto.setLocation(geoLocationService.getLocation(profile.getId()));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Cacheable(value = "decks", key = "#userId")
    public List<ProfileResponseDTO> getDeck(Long userId) {

        return generateDeck(userId);
    }

    @Scheduled(fixedRate = 60 * 60 * 1000) // Каждые 60 минут
    public void refreshAllDecks() {

        List<Long> allUserIds = profileRepository.findAllIds();

        allUserIds.forEach(this::generateDeck);
    }

}


