# The first stage of the Dockerfile executes the Gradle build
FROM gradle:8.3-jdk21-alpine as builder

# Copy your source code into the Docker image
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src

# Run the Gradle build
RUN gradle clean bootJar --no-daemon

# The second stage of the Dockerfile packages the application jar with Corretto jdk
FROM amazoncorretto:21-alpine-jdk

# Creating new nonroot user
RUN adduser --disabled-password --gecos '' backend

# Pointing to user's directory
WORKDIR /home/backend

# Swapping user
USER backend

#Copy the executable JAR file from builder
COPY --chown=backend:backend --from=builder /home/gradle/src/boot/build/libs/*.jar ./
RUN cp ./*.jar app.jar

#Expose tomcat server port
EXPOSE 8080

# Set the startup command to execute the jar
ENTRYPOINT java $RUN_ARGS -jar app.jar
