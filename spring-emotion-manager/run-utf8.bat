@echo off
REM UTF-8 인코딩 설정
chcp 65001 >nul 2>&1

REM 콘솔 폰트를 UTF-8을 지원하는 폰트로 변경 시도
powershell -Command "& {$Host.UI.RawUI.WindowTitle = 'Spring Boot Emotion Manager'}" >nul 2>&1

cls
echo.
echo ================================================================
echo    Spring Boot Emotion Manager Application Test
echo ================================================================
echo.

REM Java 버전 확인
echo Checking Java installation...
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo [ERROR] Java is not installed or not in PATH
    echo [INFO] Please install Java 17 or later
    echo.
    pause
    exit /b 1
)

echo [OK] Java is installed
echo.

REM 프로젝트 디렉토리로 이동
cd /d "%~dp0"

REM target/classes 디렉토리 생성
if not exist "target\classes" mkdir target\classes

echo Compiling simple test application...
echo ----------------------------------------------------------------
javac -d target\classes src\main\java\com\emotion\manager\SimpleTestApplication.java

if %errorlevel% neq 0 (
    echo [ERROR] Compilation failed.
    echo.
    pause
    exit /b 1
)

echo [OK] Compilation successful
echo.
echo Running simple test application...
echo ================================================================
java -cp target\classes com.emotion.manager.SimpleTestApplication

echo.
echo ================================================================
echo            Full Spring Boot Web Application Setup
echo ================================================================
echo 1. Install Maven or use an IDE ^(IntelliJ IDEA recommended^)
echo 2. Open project in IDE and run EmotionManagerApplication.java
echo 3. Or use Maven command: mvn spring-boot:run
echo 4. Then access: http://localhost:8080
echo.
echo ================================================================
echo.
pause
