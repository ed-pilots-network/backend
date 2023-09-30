package io.edpn.backend.exploration.adapter.kafka.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.edpn.backend.exploration.application.port.incomming.ReceiveKafkaMessageUseCase;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemDataRequest;
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
class SystemCoordinatesRequestMessageProcessorTest {

    @Mock
    private ReceiveKafkaMessageUseCase<SystemDataRequest> receiveSystemDataRequestUseCase;

    @Mock
    private ObjectMapper objectMapper;

    private MessageProcessor<SystemDataRequest> underTest;

    @BeforeEach
    void setUp() {
        underTest = new SystemCoordinatesRequestMessageProcessor(receiveSystemDataRequestUseCase, objectMapper);
    }

    @Test
    void listen_shouldInvokeUseCaseWithCorrectSystemDataRequest() throws JsonProcessingException {

        JsonNode jsonNode = mock(JsonNode.class);
        SystemDataRequest systemDataRequest = mock(SystemDataRequest.class);

        when(objectMapper.treeToValue(jsonNode, SystemDataRequest.class)).thenReturn(systemDataRequest);


        underTest.listen(jsonNode);


        verify(receiveSystemDataRequestUseCase, times(1)).receive(any(SystemDataRequest.class));
    }

    @Test
    public void testProcessJson_withRequestingModule() throws JsonProcessingException {
        // Prepare test data
        String requestingModule = "module";
        String requestingModuleUpperCase = requestingModule.toUpperCase();

        ObjectNode jsonNode = new ObjectNode(JsonNodeFactory.instance);
        jsonNode.put("requestingModule", requestingModule);
        SystemDataRequest systemDataRequest = mock(SystemDataRequest.class);

        // Set up mocks
        when(objectMapper.treeToValue(jsonNode, SystemDataRequest.class)).thenReturn(systemDataRequest);

        // Call the method under test
        SystemDataRequest result = underTest.processJson(jsonNode);

        // Assert results
        assertThat(systemDataRequest, is(result));
        assertThat(requestingModuleUpperCase, is(jsonNode.get("requestingModule").asText()));

        // Verify interactions
        verify(objectMapper).treeToValue(jsonNode, SystemDataRequest.class);
    }
}
