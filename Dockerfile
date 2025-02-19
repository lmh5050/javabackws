# 1. Gradle을 사용하여 애플리케이션 빌드하기 위한 빌드 단계
FROM gradle:7.5.1-jdk17 AS build

WORKDIR /app

# 프로젝트 소스 코드 복사
COPY . .

# Gradle을 사용하여 Spring Boot JAR 파일 빌드
RUN ./gradlew clean bootJar

# 2. 실제 실행 단계 (JAR 파일을 실행하기 위한 단계)
FROM openjdk:17-jdk-slim

# 빌드 단계에서 생성된 JAR 파일을 실행 단계로 복사
COPY --from=build /app/build/libs/*.jar /app/app.jar

# Spring Boot 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "/app/app.jar"]