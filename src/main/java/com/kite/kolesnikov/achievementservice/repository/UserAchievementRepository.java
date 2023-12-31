package com.kite.kolesnikov.achievementservice.repository;

import com.kite.kolesnikov.achievementservice.model.UserAchievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAchievementRepository extends JpaRepository<UserAchievement, Long> {

    boolean existsByUserIdAndAchievementId(long userId, long achievementId);

    @Query(nativeQuery = true, value = """
                    INSERT INTO user_achievement (user_id, achievement_id)
                    VALUES (:userId, :achievementId)
                    ON CONFLICT DO NOTHING
            """)
    @Modifying
    void createUserAchievementIfNecessary(@Param("userId") long userId, @Param("achievementId") long achievementId);

    List<UserAchievement> findByUserId(long userId);
}
