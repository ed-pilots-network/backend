package io.edpn.backend.exploration.adapter.kafka.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.edpn.backend.exploration.application.port.incomming.ReceiveKafkaMessageUseCase;
import io.edpn.backend.exploration.application.domain.intermodulecommunication.StationDataRequest;
import io.edpn.backend.messageprocessorlib.infrastructure.kafka.processor.MessageProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StationDataRequestMessageProcessorTest {

    @Mock
    private ReceiveKafkaMessageUseCase<StationDataRequest> receiveStationDataRequestUseCase;

    @Mock
    private ObjectMapper objectMapper;

    private MessageProcessor<StationDataRequest> underTest;

    @BeforeEach
    void setUp() {
        underTest = new StationDataRequestMessageProcessor(receiveStationDataRequestUseCase, objectMapper);
    }

    @Test
    void listen_shouldInvokeUseCaseWithCorrectStationDataRequest() throws JsonProcessingException {

        JsonNode jsonNode = mock(JsonNode.class);
        StationDataRequest systemDataRequest = mock(StationDataRequest.class);

        when(objectMapper.treeToValue(jsonNode, StationDataRequest.class)).thenReturn(systemDataRequest);


        underTest.listen(jsonNode);


        verify(receiveStationDataRequestUseCase, times(1)).receive(any(StationDataRequest.class));
    }

    @Test
    public void testProcessJson_withRequestingModule() throws JsonProcessingException {
        // Prepare test data
        String requestingModule = "module";
        String requestingModuleUpperCase = requestingModule.toUpperCase();

        ObjectNode jsonNode = new ObjectNode(JsonNodeFactory.instance);
        jsonNode.put("requestingModule", requestingModule);
        StationDataRequest systemDataRequest = mock(StationDataRequest.class);

        // Set up mocks
        when(objectMapper.treeToValue(jsonNode, StationDataRequest.class)).thenReturn(systemDataRequest);

        // Call the method under test
        StationDataRequest result = underTest.processJson(jsonNode);

        // Assert results
        assertThat(systemDataRequest, is(result));
        assertThat(requestingModuleUpperCase, is(jsonNode.get("requestingModule").asText()));

        // Verify interactions
        verify(objectMapper).treeToValue(jsonNode, StationDataRequest.class);
    }
}
