package io.edpn.backend.trade.adapter.kafka.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemEliteIdResponse;
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
class SystemEliteIdMessageProcessorTest {
    
    @Mock
    private ReceiveKafkaMessageUseCase<SystemEliteIdResponse> receiveKafkaMessageUseCase;
    
    @Mock
    private ObjectMapper objectMapper;
    
    private MessageProcessor<SystemEliteIdResponse> underTest;
    
    @BeforeEach
    void setup() {
        underTest = new SystemEliteIdResponseMessageProcessor(receiveKafkaMessageUseCase, objectMapper);
    }
    
    @Test
    void listen_shouldInvokeUseCaseWithCorrectSystemEliteIdResponse() throws JsonProcessingException {
        JsonNode jsonNode = mock(JsonNode.class);
        SystemEliteIdResponse systemEliteIdResponse = new SystemEliteIdResponse();
        
        Mockito.when(objectMapper.treeToValue(jsonNode, SystemEliteIdResponse.class)).thenReturn(systemEliteIdResponse);
        
        underTest.listen(jsonNode);
        
        verify(receiveKafkaMessageUseCase, times(1)).receive(any(SystemEliteIdResponse.class));
        
        
    }
    
}