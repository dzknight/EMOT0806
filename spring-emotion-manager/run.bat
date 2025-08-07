@echo off
chcp 65001 >nul
echo Starting Spring Boot Emotion Manager Application Test...
echo.

REM Java 설치 확인
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo Error: Java is not installed or not in PATH
    echo Please install Java 17 or later
    pause
    exit /b 1
)

REM 프로젝트 디렉토리로 이동
cd /d "%~dp0"

REM target/classes 디렉토리 생성
if not exist "target\classes" mkdir target\classes

echo Compiling simple test application...
REM 간단한 테스트 애플리케이션 컴파일
javac -d target\classes src\main\java\com\emotion\manager\SimpleTestApplication.java

if %errorlevel% neq 0 (
    echo Compilation failed.
    pause
    exit /b 1
)

echo.
echo Running simple test application...
echo.
java -cp target\classes com.emotion.manager.SimpleTestApplication

echo.
echo.
echo ===============================================
echo For the full Spring Boot web application:
echo ===============================================
echo 1. Install Maven or use an IDE ^(IntelliJ IDEA recommended^)
echo 2. Open project in IDE and run EmotionManagerApplication.java
echo 3. Or use Maven: mvn spring-boot:run
echo 4. Access: http://localhost:8080
echo.
pause
