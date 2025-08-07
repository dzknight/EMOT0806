# 감정 분석 시스템

이 프로젝트는 Flask 기반의 AI 감정 분석 시스템과 Spring Boot 기반의 관리 시스템으로 구성되어 있습니다.

## 시스템 구성

### 1. Flask 감정 분석 앱 (포트 5000)
- **위치**: `d:\EMOT0806\`
- **기능**: DeepFace를 이용한 얼굴 감정 분석
- **주요 페이지**:
  - `/` - 이미지 업로드 및 분석
  - `/history` - 분석 기록 조회
  - `/error` - 얼굴 감지 실패 시 에러 페이지

### 2. Spring Boot 관리 시스템 (포트 8080)
- **위치**: `d:\EMOT0806\spring-emotion-manager\`
- **기능**: 분석 결과 조회 및 통계
- **주요 페이지**:
  - `/` - 대시보드 (통계 및 차트)
  - `/results` - 분석 결과 목록
  - `/result/{id}` - 상세 결과 조회
- **REST API**:
  - `/api/results` - 모든 결과 조회
  - `/api/result/{id}` - 특정 결과 조회
  - `/api/statistics` - 감정별 통계

## 데이터베이스 설정

### MariaDB 설치 및 설정
1. MariaDB 설치
2. 데이터베이스 생성: `database_setup.sql` 실행
```sql
mysql -u root -p < database_setup.sql
```

### 데이터베이스 연결 정보
- **호스트**: localhost:3306
- **데이터베이스**: emotion_analysis
- **사용자**: root
- **비밀번호**: password (필요시 변경)

## 사전 요구사항

### Flask 앱
- Python 3.10+
- 가상환경 (이미 구성됨)

### Spring Boot 앱
- **Java 17+** (필수)
- **Maven** (권장) 또는 **IDE** (IntelliJ IDEA, Eclipse)

### Maven 설치 방법 (Windows)
1. [Apache Maven 다운로드](https://maven.apache.org/download.cgi)
2. 압축 해제 후 PATH 환경변수에 `bin` 폴더 추가
3. 확인: `mvn -version`

### IDE 사용 권장
- **IntelliJ IDEA Community** (무료): Spring Boot 자동 지원
- **Eclipse**: Spring Tools Suite 플러그인 설치

## 실행 방법

### 1. Flask 앱 실행
```bash
cd d:\EMOT0806
D:/EMOT0806/.venv/Scripts/python.exe app.py
```
- 브라우저에서 `http://localhost:5000` 접속

### 2. Spring Boot 앱 실행

**옵션 1: Maven이 설치되어 있는 경우**
```cmd
cd d:\EMOT0806\spring-emotion-manager
mvn spring-boot:run
```

**옵션 2: IDE 사용 (가장 쉬운 방법)**

**IntelliJ IDEA:**
1. `File > Open` → `d:\EMOT0806\spring-emotion-manager` 선택
2. Maven 프로젝트 자동 인식 및 의존성 다운로드
3. `src/main/java/com/emotion/manager/EmotionManagerApplication.java` 우클릭
4. `Run 'EmotionManagerApplication'` 클릭

**Eclipse + Spring Tools:**
1. `File > Import > Existing Maven Projects`
2. `d:\EMOT0806\spring-emotion-manager` 선택
3. 프로젝트 우클릭 → `Run As > Spring Boot App`

**Visual Studio Code:**
1. Java Extension Pack 설치
2. 폴더 열기 → `d:\EMOT0806\spring-emotion-manager`
3. `EmotionManagerApplication.java` 파일에서 `Run` 버튼 클릭

**옵션 3: JAR 파일로 빌드 후 실행**
```cmd
cd d:\EMOT0806\spring-emotion-manager
# IDE에서 Maven 빌드 또는 수동 컴파일 후
java -jar target\emotion-manager-0.0.1-SNAPSHOT.jar
```
- 브라우저에서 `http://localhost:8080` 접속

## 기능 설명

### Flask 앱
1. **이미지 업로드**: 얼굴 사진을 업로드하여 감정 분석
2. **실시간 미리보기**: 업로드 전 이미지 미리보기
3. **결과 저장**: 분석 결과를 MariaDB에 자동 저장
4. **에러 처리**: 얼굴 감지 실패 시 친절한 에러 페이지

### Spring Boot 관리 시스템
1. **대시보드**: 
   - 총 분석 수 표시
   - 감정별 통계 차트 (Chart.js)
   - 최근 분석 결과 목록
2. **결과 관리**:
   - 모든 분석 결과 조회
   - 감정별 필터링
   - 상세 결과 보기
3. **REST API**: 외부 시스템 연동용 API 제공

## 주요 기술 스택

### Flask 앱
- Python 3.10
- Flask + SQLAlchemy
- DeepFace (AI 감정 분석)
- TensorFlow
- Bulma CSS
- PyMySQL

### Spring Boot 앱
- Java 17
- Spring Boot 3.2
- Spring Data JPA
- Thymeleaf
- Bootstrap 5
- Chart.js
- MariaDB JDBC

## 트러블슈팅

### 1. 패키지 의존성 문제
```bash
# Flask 환경
D:/EMOT0806/.venv/Scripts/pip.exe install PyMySQL SQLAlchemy Flask-SQLAlchemy

# Maven 의존성
mvn clean install

# Maven Wrapper 사용 (Windows)
mvnw.cmd clean install
```

### 2. Maven/Java 환경 문제
- **Java 17 설치 확인**: `java -version`
- **JAVA_HOME 환경변수 설정**
- **Maven 설치 확인**: `mvn -version` 또는 Maven Wrapper 사용

### 3. 데이터베이스 연결 실패
- MariaDB 서비스 실행 상태 확인
- 연결 정보 (호스트, 포트, 사용자명, 비밀번호) 확인
- `application.properties` 및 Flask `app.py` 설정 확인

### 4. 포트 충돌
- Flask: 5000번 포트 사용
- Spring Boot: 8080번 포트 사용
- 필요시 `application.properties`에서 `server.port` 변경

## 배포 시 주의사항

1. **보안**: 
   - 데이터베이스 비밀번호 변경
   - Flask DEBUG 모드 비활성화
2. **성능**: 
   - 이미지 파일 크기 제한
   - 데이터베이스 인덱스 최적화
3. **확장성**: 
   - 파일 저장소 분리 (AWS S3 등)
   - 로드 밸런싱 고려
