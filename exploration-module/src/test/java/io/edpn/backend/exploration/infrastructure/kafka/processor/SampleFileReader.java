package io.edpn.backend.exploration.infrastructure.kafka.processor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.exploration.util.ClasspathFileScanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.stream.Stream;

public class SampleFileReader {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static Stream<JsonNode> readSampleFiles(String sampleDirectory) {
        Objects.requireNonNull(sampleDirectory, "Parameter sampleDirectory is null");
        String delimiter = sampleDirectory.endsWith("/") ? "" : "/";
        return ClasspathFileScanner.listFiles(sampleDirectory).stream()
                .filter(fileName -> fileName.endsWith(".json"))
                .map(fileName -> String.join(delimiter, sampleDirectory, fileName))
                .map(SampleFileReader::readFile);

    }

    private static JsonNode readFile(String filePath) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)))) {
            return OBJECT_MAPPER.readTree(reader);
        } catch (IOException e) {
            throw new RuntimeException("Reading resource file '" + filePath + "' failed", e);
        }
    }
}
