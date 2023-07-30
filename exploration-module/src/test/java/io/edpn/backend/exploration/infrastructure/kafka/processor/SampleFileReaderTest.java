package io.edpn.backend.exploration.infrastructure.kafka.processor;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SampleFileReaderTest {

    private static final String SAMPLE_FILES_PATH = "data/eddn-observed-messages/approachsettlement/1_dir/";

    @Test
    public void readsSampleFiles() {
        Set<JsonNode> sampleFiles = SampleFileReader.readSampleFiles(SAMPLE_FILES_PATH).collect(Collectors.toSet());
        assertEquals(6, sampleFiles.size());
    }

}
