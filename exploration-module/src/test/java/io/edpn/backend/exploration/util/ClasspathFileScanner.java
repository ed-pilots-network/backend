package io.edpn.backend.exploration.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class ClasspathFileScanner {

    public static Set<String> listFiles(String resourcePath) {
        Objects.requireNonNull(resourcePath, "Resource path parameter is null");
        try (InputStream inputStream = getResourceStream(resourcePath)) {
            return readFileNames(inputStream);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to list files for resource path: " + resourcePath, e);
        }
    }

    private static InputStream getResourceStream(String resourcePath) throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL resourceUrl = classLoader.getResource(resourcePath);
        if (resourceUrl == null) {
            throw new IllegalArgumentException("Resource path '" + resourcePath + "' not found or inaccessible");
        }
        try {
            Path path = Path.of(resourceUrl.toURI());
            if (path.toFile().isDirectory()) {
                return resourceUrl.openStream();
            }
            throw new IllegalArgumentException("Resource path '" + resourcePath + "' does not represent a directory");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private static Set<String> readFileNames(InputStream inputStream) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            return reader.lines().collect(Collectors.toSet());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
