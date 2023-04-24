# Use Alpine-based OpenJDK 17 image as the base container
FROM openjdk:17-alpine

# Set working directory
WORKDIR /app

# Copy Maven wrapper and pom.xml to the working directory
COPY mvnw ./
COPY .mvn .mvn
COPY pom.xml ./

# Download dependencies
RUN ./mvnw dependency:go-offline -B

# Copy source files
COPY src src

# Build the application
RUN ./mvnw clean install -DskipTests

# Copy the executable JAR file
RUN cp target/*.jar app.jar

# Set the entrypoint
ENTRYPOINT ["java", "-jar", "app.jar"]
