package io.edpn.backend.trade.adapter.kafka.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.CommodityMessage;
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
class CommodityV3MessageProcessorTest {
    
    @Mock
    private ReceiveKafkaMessageUseCase<CommodityMessage.V3> receiveKafkaMessageUseCase;
    
    @Mock
    private ObjectMapper objectMapper;
    
    private MessageProcessor<CommodityMessage.V3> underTest;
    
    @BeforeEach
    void setup() {
        underTest = new CommodityV3MessageProcessor(receiveKafkaMessageUseCase, objectMapper);
    }
    
    @Test
    void listen_shouldInvokeUseCaseWithCorrectCommodityV3Message() throws JsonProcessingException {
        JsonNode jsonNode = mock(JsonNode.class);
        CommodityMessage.V3 commodityMessage = new CommodityMessage.V3();
        
        Mockito.when(objectMapper.treeToValue(jsonNode, CommodityMessage.V3.class)).thenReturn(commodityMessage);
        
        underTest.listen(jsonNode);
        
        verify(receiveKafkaMessageUseCase, times(1)).receive(any(CommodityMessage.V3.class));
        
        
    }
    
}