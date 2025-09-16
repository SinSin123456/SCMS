# Use official OpenJDK 21 slim image
FROM openjdk:21-jdk-slim

# Set working directory
WORKDIR /app

# Copy the built JAR and properties files
COPY target/SCMS-0.0.1-SNAPSHOT.jar app.jar
COPY src/main/resources/application.properties /app/
COPY src/main/resources/application-docker.properties /app/

# Expose port
EXPOSE 8080

# Run Spring Boot app with Docker profile
ENTRYPOINT ["java","-jar","app.jar","--spring.profiles.active=docker"]
