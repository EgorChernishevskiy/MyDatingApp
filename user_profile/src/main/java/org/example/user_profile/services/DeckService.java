package org.example.user_profile.services;

import lombok.RequiredArgsConstructor;
import org.example.user_profile.dto.responses.PreferencesResponseDTO;
import org.example.user_profile.dto.responses.ProfileResponseDTO;
import org.example.user_profile.entities.ProfileEntity;
import org.example.user_profile.mappers.ProfileMapper;
import org.example.user_profile.repositories.ProfileRepository;
import org.springframework.cache.annotation.CacheEvict;
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

        return candidates.stream()
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


