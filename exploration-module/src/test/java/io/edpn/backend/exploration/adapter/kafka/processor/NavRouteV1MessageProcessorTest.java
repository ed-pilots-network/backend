package io.edpn.backend.exploration.adapter.kafka.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.exploration.application.port.incomming.ReceiveKafkaMessageUseCase;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.NavRouteMessage;
import io.edpn.backend.messageprocessorlib.infrastructure.kafka.processor.MessageProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class NavRouteV1MessageProcessorTest {

    @Mock
    private ReceiveKafkaMessageUseCase<NavRouteMessage.V1> receiveKafkaMessageUseCase;

    @Mock
    private ObjectMapper objectMapper;

    private MessageProcessor<NavRouteMessage.V1> underTest;

    @BeforeEach
    void setUp() {
        underTest = new NavRouteV1MessageProcessor(receiveKafkaMessageUseCase, objectMapper);
    }

    @Test
    void listen_shouldInvokeUseCaseWithCorrectNavRouteMessage() throws JsonProcessingException {
        // Given
        JsonNode jsonNode = mock(JsonNode.class);
        NavRouteMessage.V1 navRouteMessage = new NavRouteMessage.V1();

        Mockito.when(objectMapper.treeToValue(jsonNode, NavRouteMessage.V1.class)).thenReturn(navRouteMessage);

        // When
        underTest.listen(jsonNode);

        // Then
        verify(receiveKafkaMessageUseCase, times(1)).receive(any(NavRouteMessage.V1.class));
    }

}