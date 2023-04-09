FROM openjdk:17-alpine

WORKDIR /app

COPY mvnw ./
COPY .mvn .mvn
COPY pom.xml ./
COPY src/main/resources/app.properties app.properties

RUN ./mvnw dependency:go-offline

COPY src src

RUN ./mvnw clean package -DskipTests

# Extract jar.name and jar.version from the app.properties file
RUN jar_name=$(grep -oP 'jar.name=\K.*' app.properties) && \
    jar_version=$(grep -oP 'jar.version=\K.*' app.properties) && \
    echo "export JAR_NAME=${jar_name}-${jar_version}.jar" > env.sh && \
    chmod +x env.sh

# Execute env.sh to set JAR_NAME environment variable
ENTRYPOINT ["/bin/sh", "-c", "source env.sh && java -jar ${JAR_NAME}"]
