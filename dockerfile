# Use the official Amazon Corretto 17 image to create a build artifact.
FROM amazoncorretto:17-alpine-full as build

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

# Use Amazon Corretto 17 for runtime
FROM amazoncorretto:17-alpine-full as runtime

WORKDIR /app

# Copy the jar to the production image from the builder stage.
COPY --from=build /app/boot/build/libs/*.jar /app

# Set the startup command to run your binary
ENTRYPOINT ["java","-jar","./boot.jar"]
