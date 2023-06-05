# The first stage of the Dockerfile executes the Gradle build
FROM gradle:8.1.1-jdk17-alpine as builder

# Copy your source code into the Docker image
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src

# Run the Gradle build
RUN gradle clean bootJar --no-daemon

# The second stage of the Dockerfile packages the application jar with OpenJDK JRE
FROM openjdk:17-jdk-alpine

# Copy the Spring Boot application jar from the builder stage to the current stage
COPY --from=builder /home/gradle/src/boot/build/libs/*.jar /app.jar

# Set the startup command to execute the jar
CMD ["java", "-jar", "/app.jar"]
