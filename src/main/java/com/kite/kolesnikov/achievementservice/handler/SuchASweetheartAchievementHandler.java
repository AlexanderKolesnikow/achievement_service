package com.kite.kolesnikov.achievementservice.handler;

import com.kite.kolesnikov.achievementservice.dto.RecommendationEventDto;
import com.kite.kolesnikov.achievementservice.service.AchievementProgressService;
import com.kite.kolesnikov.achievementservice.service.AchievementService;
import com.kite.kolesnikov.achievementservice.service.UserAchievementService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SuchASweetheartAchievementHandler extends AbstractAchievementHandler<RecommendationEventDto> {

    public SuchASweetheartAchievementHandler(AchievementService achievementService,
                                             AchievementProgressService achievementProgressService,
                                             UserAchievementService userAchievementService,
                                             @Value("${such_a_sweetheart.title}") String title) {
        super(achievementService, achievementProgressService, userAchievementService, title);
    }

    @Override
    public void handle(RecommendationEventDto event) {
        handleAchievement(event.getReceiverId());
    }
}
