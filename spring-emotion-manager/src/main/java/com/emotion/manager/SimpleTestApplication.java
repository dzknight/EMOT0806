package com.emotion.manager;

/**
 * Spring Boot 의존성 없이 실행할 수 있는 간단한 테스트 애플리케이션
 * 실제 웹 애플리케이션은 IDE나 Maven을 통해 실행해야 합니다.
 */
public class SimpleTestApplication {
    
    public static void main(String[] args) {
        System.out.println("=".repeat(50));
        System.out.println("    감정 분석 관리 시스템 - 테스트 모드");
        System.out.println("=".repeat(50));
        System.out.println();
        
        // Java 환경 정보 출력
        System.out.println("Java 정보:");
        System.out.println("  버전: " + System.getProperty("java.version"));
        System.out.println("  공급업체: " + System.getProperty("java.vendor"));
        System.out.println("  경로: " + System.getProperty("java.home"));
        System.out.println();
        
        // 시스템 정보 출력
        System.out.println("시스템 정보:");
        System.out.println("  OS: " + System.getProperty("os.name") + " " + System.getProperty("os.version"));
        System.out.println("  아키텍처: " + System.getProperty("os.arch"));
        System.out.println("  사용자: " + System.getProperty("user.name"));
        System.out.println();
        
        // 데이터베이스 연결 시뮬레이션
        System.out.println("데이터베이스 연결 테스트:");
        simulateDatabaseConnection();
        System.out.println();
        
        // 샘플 감정 데이터 표시
        System.out.println("샘플 감정 분석 데이터:");
        displaySampleEmotionData();
        System.out.println();
        
        // 실행 안내
        System.out.println("실제 웹 애플리케이션 실행 방법:");
        System.out.println("1. IntelliJ IDEA 또는 Eclipse에서 프로젝트 열기");
        System.out.println("2. EmotionManagerApplication.java 실행");
        System.out.println("3. http://localhost:8080 접속");
        System.out.println();
        System.out.println("또는 Maven 사용:");
        System.out.println("   mvn spring-boot:run");
        System.out.println();
        System.out.println("테스트 완료!");
    }
    
    private static void simulateDatabaseConnection() {
        try {
            System.out.print("  MariaDB 연결 확인 중");
            for (int i = 0; i < 3; i++) {
                Thread.sleep(500);
                System.out.print(".");
            }
            System.out.println(" ✓");
            System.out.println("  데이터베이스: emotion_analysis");
            System.out.println("  테이블: emotion_results");
            System.out.println("  상태: 준비됨 (실제 연결은 Spring Boot 실행 시)");
        } catch (InterruptedException e) {
            System.out.println(" ✗");
        }
    }
    
    private static void displaySampleEmotionData() {
        String[][] sampleData = {
            {"1", "2025-08-07 10:30", "happy", "85.6%"},
            {"2", "2025-08-07 11:15", "sad", "78.4%"},
            {"3", "2025-08-07 12:00", "angry", "72.8%"},
            {"4", "2025-08-07 13:45", "surprise", "65.7%"},
            {"5", "2025-08-07 14:20", "neutral", "57.7%"}
        };
        
        System.out.printf("  %-5s %-15s %-10s %-8s%n", "ID", "시간", "감정", "신뢰도");
        System.out.println("  " + "-".repeat(40));
        
        for (String[] row : sampleData) {
            System.out.printf("  %-5s %-15s %-10s %-8s%n", row[0], row[1], row[2], row[3]);
        }
    }
}
