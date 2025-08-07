-- MariaDB 데이터베이스 생성 스크립트

-- 데이터베이스 생성
CREATE DATABASE IF NOT EXISTS emotion_analysis 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

-- 사용자 생성 및 권한 부여 (선택사항)
-- CREATE USER 'emotion_user'@'localhost' IDENTIFIED BY 'emotion_password';
-- GRANT ALL PRIVILEGES ON emotion_analysis.* TO 'emotion_user'@'localhost';
-- FLUSH PRIVILEGES;

-- 데이터베이스 선택
USE emotion_analysis;

-- emotion_results 테이블 생성
CREATE TABLE IF NOT EXISTS emotion_results (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    image_filename VARCHAR(255) NOT NULL,
    dominant_emotion VARCHAR(50) NOT NULL,
    emotion_scores TEXT NOT NULL,
    analysis_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_analysis_time (analysis_time),
    INDEX idx_dominant_emotion (dominant_emotion)
);

-- 샘플 데이터 삽입 (테스트용)
INSERT INTO emotion_results (image_filename, dominant_emotion, emotion_scores, analysis_time) VALUES
('sample1.jpg', 'happy', '{"happy": 85.6, "sad": 5.2, "angry": 3.1, "surprise": 2.8, "fear": 1.8, "disgust": 1.2, "neutral": 0.3}', '2025-08-07 10:30:00'),
('sample2.jpg', 'sad', '{"happy": 8.1, "sad": 78.4, "angry": 5.2, "surprise": 3.1, "fear": 2.9, "disgust": 1.8, "neutral": 0.5}', '2025-08-07 11:15:00'),
('sample3.jpg', 'angry', '{"happy": 3.2, "sad": 12.1, "angry": 72.8, "surprise": 4.5, "fear": 3.2, "disgust": 2.8, "neutral": 1.4}', '2025-08-07 12:00:00'),
('sample4.jpg', 'surprise', '{"happy": 15.2, "sad": 8.1, "angry": 5.4, "surprise": 65.7, "fear": 2.8, "disgust": 1.9, "neutral": 0.9}', '2025-08-07 13:45:00'),
('sample5.jpg', 'neutral', '{"happy": 12.3, "sad": 8.7, "angry": 6.2, "surprise": 7.1, "fear": 4.8, "disgust": 3.2, "neutral": 57.7}', '2025-08-07 14:20:00');

-- 테이블 구조 확인
DESCRIBE emotion_results;

-- 데이터 확인
SELECT * FROM emotion_results ORDER BY analysis_time DESC LIMIT 10;
