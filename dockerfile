# Use the official maven/Java 8 image to create a build artifact.
# This is based on Debian and sets the working directory to /app
FROM openjdk:17-jdk-alpine as build

# Set the current working directory inside the image
WORKDIR /app

# Copy gradle/wrapper/gradle-wrapper.jar
COPY gradlew .
COPY gradle gradle

# Copy build.gradle, settings.gradle and other Gradle configuration files
COPY build.gradle .
COPY settings.gradle .

# Copy your source code into the image
COPY src src

# Build the project and its dependencies.
RUN ./gradlew bootJar

# Use AdoptOpenJDK for base image
FROM adoptopenjdk:8-jdk-hotspot as runtime

WORKDIR /app

# Copy the jar to the production image from the builder stage.
COPY --from=build /app/boot/build/libs/*.jar /app

# Set the startup command to run your binary
ENTRYPOINT ["java","-jar","./<your-application-name>.jar"]
