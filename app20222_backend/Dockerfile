# Build command: docker build --tag=app20222_backend:1.0.0 .
FROM openjdk:11.0.16
MAINTAINER chiendao1808
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app20222_backend.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app20222_backend.jar"]
