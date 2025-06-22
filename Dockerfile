# 1. JDK 17 기반 이미지 사용
FROM openjdk:21

# 2. 작업 디렉터리 설정 (선택적)
WORKDIR /app

# 3. 빌드된 jar 파일을 컨테이너 내부로 복사
COPY ./build/libs/newsletter-service.jar app.jar

# 4. 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]