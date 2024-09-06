# ToyProject - 온라인 도서 관리 시스템

이 프로젝트는 도서를 검색, 대여, 관리할 수 있는 웹 애플리케이션입니다.

## 사용된 기술

- **프론트엔드:**
  - React: 18.3.1
  - Vite: 5.4.1
- **백엔드:**
  - Spring Boot: 3.3.2
  - Spring Data JPA
  - Oracle Database: 21c Express Edition Release 21.0.0.0.0 - Production Version 21.3.0.0.0
- **개발 도구:**
  - JDK: Amazon Corretto 17
  - Spring Tool Suite (STS): 4.24.0.RELEASE
  - SQL Developer: 23.1.1.345
  - Lombok: 1.18.32
  - Node.js: v20.15.1

## Vite 사용 이유

- **빠른 개발 서버**: 개발자에게 즉각적인 피드백 루프를 제공합니다.
- **효율적인 HMR (Hot Module Replacement)**: 빠른 코드 업데이트를 위한 핫 모듈 교체 기능을 지원합니다.
- **최신 웹 기술 지원**: 애플리케이션이 최신 브라우저에서 원활하게 작동하도록 보장합니다.

## 설정

3. 프로젝트 설정:

```bash
# 프론트엔드 설정
cd frontend
npm install
npm run dev

# 백엔드 설정
cd backend
./mvnw spring-boot:run