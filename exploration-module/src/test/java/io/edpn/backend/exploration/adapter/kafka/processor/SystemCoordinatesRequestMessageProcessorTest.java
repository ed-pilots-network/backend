package io.edpn.backend.exploration.adapter.kafka.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.exploration.application.port.incomming.ReceiveKafkaMessageUseCase;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemDataRequest;
import io.edpn.backend.messageprocessorlib.infrastructure.kafka.processor.MessageProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
        // Given
        JsonNode jsonNode = Mockito.mock(JsonNode.class);
        SystemDataRequest systemDataRequest = new SystemDataRequest();

        Mockito.when(objectMapper.treeToValue(jsonNode, SystemDataRequest.class)).thenReturn(systemDataRequest);

        // When
        underTest.listen(jsonNode);

        // Then
        verify(receiveSystemDataRequestUseCase, times(1)).receive(any(SystemDataRequest.class));
    }
}
