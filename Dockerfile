#자바 17 베이스 이미지
FROM openjdk:17


#이미지 생성할 때 파일을 복사한다. 위의 jar_file을 app.jar로 복사해서 이미지로 가져온다.
COPY *.jar app.jar

#컨테이너를 시작할 때 실행할 명령어.
ENTRYPOINT ["java", "-jar", "app.jar"]

EXPOSE 8081