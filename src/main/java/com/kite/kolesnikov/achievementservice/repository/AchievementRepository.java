package com.kite.kolesnikov.achievementservice.repository;

import com.kite.kolesnikov.achievementservice.model.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AchievementRepository extends JpaRepository<Achievement, Long> {

    Optional<Achievement> getAchievementByTitle(String title);
}
