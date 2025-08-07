package com.emotion.manager.repository;

import com.emotion.manager.entity.EmotionResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EmotionResultRepository extends JpaRepository<EmotionResult, Long> {
    
    // 최신 분석 결과를 가져오는 메서드
    List<EmotionResult> findTop50ByOrderByAnalysisTimeDesc();
    
    // 특정 감정으로 필터링
    List<EmotionResult> findByDominantEmotionOrderByAnalysisTimeDesc(String dominantEmotion);
    
    // 날짜 범위로 필터링
    List<EmotionResult> findByAnalysisTimeBetweenOrderByAnalysisTimeDesc(LocalDateTime start, LocalDateTime end);
    
    // 감정별 통계
    @Query("SELECT e.dominantEmotion, COUNT(e) FROM EmotionResult e GROUP BY e.dominantEmotion ORDER BY COUNT(e) DESC")
    List<Object[]> getEmotionStatistics();
}
