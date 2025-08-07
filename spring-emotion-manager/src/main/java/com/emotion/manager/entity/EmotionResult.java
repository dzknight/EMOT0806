package com.emotion.manager.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "emotion_results")
public class EmotionResult {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "image_filename", nullable = false)
    private String imageFilename;
    
    @Column(name = "dominant_emotion", nullable = false)
    private String dominantEmotion;
    
    @Column(name = "emotion_scores", columnDefinition = "TEXT", nullable = false)
    private String emotionScores;
    
    @Column(name = "analysis_time")
    private LocalDateTime analysisTime;
    
    // 기본 생성자
    public EmotionResult() {}
    
    // 생성자
    public EmotionResult(String imageFilename, String dominantEmotion, String emotionScores, LocalDateTime analysisTime) {
        this.imageFilename = imageFilename;
        this.dominantEmotion = dominantEmotion;
        this.emotionScores = emotionScores;
        this.analysisTime = analysisTime;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getImageFilename() {
        return imageFilename;
    }
    
    public void setImageFilename(String imageFilename) {
        this.imageFilename = imageFilename;
    }
    
    public String getDominantEmotion() {
        return dominantEmotion;
    }
    
    public void setDominantEmotion(String dominantEmotion) {
        this.dominantEmotion = dominantEmotion;
    }
    
    public String getEmotionScores() {
        return emotionScores;
    }
    
    public void setEmotionScores(String emotionScores) {
        this.emotionScores = emotionScores;
    }
    
    public LocalDateTime getAnalysisTime() {
        return analysisTime;
    }
    
    public void setAnalysisTime(LocalDateTime analysisTime) {
        this.analysisTime = analysisTime;
    }
    
    @Override
    public String toString() {
        return "EmotionResult{" +
                "id=" + id +
                ", imageFilename='" + imageFilename + '\'' +
                ", dominantEmotion='" + dominantEmotion + '\'' +
                ", analysisTime=" + analysisTime +
                '}';
    }
}
