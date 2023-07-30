package io.edpn.backend.exploration.adapter.kafka.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.exploration.infrastructure.kafka.processor.SampleFileReader;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.ApproachSettlementMessage;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

public class ApproachSettlementMessageProcessorTest {

    private static final String SAMPLE_FILES_PATH = "data/eddn-observed-messages/approachsettlement/1_dir/";
    private final ApproachSettelementMessageProcessor processor = new ApproachSettelementMessageProcessor(null, new ObjectMapper());

    @ParameterizedTest
    @MethodSource
    public void convertsJsonNodeToPojo(JsonNode jsonNode) {
        try {
            ApproachSettlementMessage.V1 pojo = processor.processJson(jsonNode);
            assertNotNull(pojo);
        } catch (JsonProcessingException e) {
            fail("Failed to convert JSON to POJO!", e);
        }
    }

    @SuppressWarnings("unused")
    private static Stream<JsonNode> convertsJsonNodeToPojo() {
        return SampleFileReader.readSampleFiles(SAMPLE_FILES_PATH);
    }
}
