package io.edpn.backend.trade.adapter.kafka.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemCoordinatesResponse;
import io.edpn.backend.messageprocessorlib.infrastructure.kafka.processor.MessageProcessor;
import io.edpn.backend.trade.application.port.incomming.kafka.ReceiveKafkaMessageUseCase;
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
class SystemCoordinateResponseMessageProcessorTest {
    
    @Mock
    private ReceiveKafkaMessageUseCase<SystemCoordinatesResponse> receiveKafkaMessageUseCase;
    
    @Mock
    private ObjectMapper objectMapper;
    
    private MessageProcessor<SystemCoordinatesResponse> underTest;
    
    @BeforeEach
    void setup() {
        underTest = new SystemCoordinatesResponseMessageProcessor(receiveKafkaMessageUseCase, objectMapper);
    }
    
    @Test
    void listen_shouldInvokeUseCaseWithCorrectSystemCoordinatesResponse() throws JsonProcessingException {
        JsonNode jsonNode = mock(JsonNode.class);
        SystemCoordinatesResponse systemCoordinatesResponse = new SystemCoordinatesResponse(null, 1.0, 2.0, 3.0);
        
        Mockito.when(objectMapper.treeToValue(jsonNode, SystemCoordinatesResponse.class)).thenReturn(systemCoordinatesResponse);
        
        underTest.listen(jsonNode);
        
        verify(receiveKafkaMessageUseCase, times(1)).receive(any(SystemCoordinatesResponse.class));
        
        
    }
    
}