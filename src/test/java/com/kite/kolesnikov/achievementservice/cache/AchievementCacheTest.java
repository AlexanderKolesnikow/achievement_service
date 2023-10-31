package com.kite.kolesnikov.achievementservice.cache;

import com.kite.kolesnikov.achievementservice.config.TestContainerConfig;
import com.kite.kolesnikov.achievementservice.exception.AchievementNotInCacheException;
import com.kite.kolesnikov.achievementservice.model.Achievement;
import com.kite.kolesnikov.achievementservice.model.Rarity;
import com.kite.kolesnikov.achievementservice.repository.AchievementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ContextConfiguration(classes = TestContainerConfig.class)
public class AchievementCacheTest {
    @Autowired
    private AchievementRepository achievementRepository;
    @Autowired
    private AchievementCache achievementCache;
    private Achievement expectedAchievement;

    @BeforeEach
    void setUp() {
        achievementRepository.deleteAll();
        expectedAchievement = Achievement.builder()
                .title("test").description("test").points(1).rarity(Rarity.COMMON).build();
        achievementRepository.save(expectedAchievement);
    }

    @Test
    public void testGetExistingAchievement() {
        Achievement cachedAchievement = achievementCache.getAchievement("test");

        assertNotNull(cachedAchievement);
        assertEquals(expectedAchievement.getTitle(), cachedAchievement.getTitle());
    }

    @Test
    public void testGetNonExistingAchievement() {
        assertThrows(AchievementNotInCacheException.class, () -> {
            achievementCache.getAchievement("non existing achievement");
        });
    }
}