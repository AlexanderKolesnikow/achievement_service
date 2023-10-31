package com.kite.kolesnikov.achievementservice.service;

import com.kite.kolesnikov.achievementservice.cache.AchievementCache;
import com.kite.kolesnikov.achievementservice.exception.AchievementNotInCacheException;
import com.kite.kolesnikov.achievementservice.exception.EntityNotFoundException;
import com.kite.kolesnikov.achievementservice.model.Achievement;
import com.kite.kolesnikov.achievementservice.repository.AchievementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AchievementService {
    private final AchievementCache achievementCache;
    private final AchievementRepository achievementRepository;

    @Transactional(readOnly = true)
    public Achievement getAchievementByTitle(String title) {
        return achievementRepository.getAchievementByTitle(title)
                .orElseThrow(() -> {
                    log.error("Achievement with title '{}' not found.", title);
                    return new EntityNotFoundException("Achievement", title);
                });
    }

    @Transactional(readOnly = true)
    public Achievement getAchievementFromCache(String title) {
        try {
            return achievementCache.getAchievement(title);
        } catch (AchievementNotInCacheException e) {
            return getAchievementByTitle(title);
        }
    }
}
