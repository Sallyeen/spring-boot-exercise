FROM openjdk:8

WORKDIR /app

ADD ../target/spring-boot-exercise.jar /app

EXPOSE 8080

CMD ["java", "-Dspring.profiles.active=docker", "-jar", "spring-boot-exercise.jar"]