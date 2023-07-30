package io.edpn.backend.exploration.util;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClasspathFileScannerTest {

    @Test
    public void listFilesFindsFilesFromResourcePath() {
        Set<String> fileNames = ClasspathFileScanner.listFiles("io/edpn/backend/exploration/util/");
        assertNotNull(fileNames);
        assertFalse(fileNames.isEmpty());
        assertTrue(fileNames.containsAll(Set.of("ClasspathFileScanner.class", "ClasspathFileScannerTest.class")));
    }
}
