# Use Alpine-based OpenJDK 17 image as the base container
FROM openjdk:17-alpine

# Set working directory
WORKDIR /app

# Copy Maven wrapper, pom.xml, and source files to the working directory
COPY mvnw ./
COPY .mvn .mvn
COPY pom.xml ./
COPY src src

# Install dependencies and build the application
RUN ./mvnw clean install -DskipTests

# Copy the executable JAR file
RUN cp target/*.jar app.jar

# Set the entrypoint
ENTRYPOINT ["java", "-jar", "app.jar"]
