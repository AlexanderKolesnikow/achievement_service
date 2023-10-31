package com.kite.kolesnikov.achievementservice.service;

import com.kite.kolesnikov.achievementservice.exception.EntityNotFoundException;
import com.kite.kolesnikov.achievementservice.model.AchievementProgress;
import com.kite.kolesnikov.achievementservice.repository.AchievementProgressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;

@Slf4j
@Service
@RequiredArgsConstructor
public class AchievementProgressService {
    private final AchievementProgressRepository achievementProgressRepository;

    @Transactional
    public void createProgressIfNotExist(long userId, long achievementId) {
        achievementProgressRepository.createProgressIfNecessary(userId, achievementId);
    }

    @Transactional(readOnly = true)
    public AchievementProgress getByUserIdAndAchievementId(long userId, long achievementId) {
        return achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId)
                .orElseThrow(() -> {
                    String value = MessageFormat.format("UserId {0} and AchievementId {1}", userId, achievementId);
                    log.error("Achievement progress with {} not found.", value);
                    return new EntityNotFoundException("Achievement progress", value);
                });
    }

    @Transactional
    public void incrementProgress(AchievementProgress achievementProgress) {
        achievementProgress.increment();
        achievementProgressRepository.save(achievementProgress);
        log.info("Progress for the achievement {} has been incremented. Current progress is {}",
                achievementProgress.getAchievement().getTitle(), achievementProgress.getCurrentPoints());
    }
}
