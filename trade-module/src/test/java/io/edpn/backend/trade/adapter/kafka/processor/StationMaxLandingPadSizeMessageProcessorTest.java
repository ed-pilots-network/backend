package io.edpn.backend.trade.adapter.kafka.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationMaxLandingPadSizeResponse;
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
class StationMaxLandingPadSizeMessageProcessorTest {
    
    @Mock
    private ReceiveKafkaMessageUseCase<StationMaxLandingPadSizeResponse> receiveKafkaMessageUseCase;
    
    @Mock
    private ObjectMapper objectMapper;
    
    private MessageProcessor<StationMaxLandingPadSizeResponse> underTest;
    
    @BeforeEach
    void setup() {
        underTest = new StationMaxLandingPadSizeResponseMessageProcessor(receiveKafkaMessageUseCase, objectMapper);
    }
    
    @Test
    void listen_shouldInvokeUseCaseWithCorrectStationMaxLandingPadSizeResponse() throws JsonProcessingException {
        JsonNode jsonNode = mock(JsonNode.class);
        StationMaxLandingPadSizeResponse stationMaxLandingPadSizeResponse = new StationMaxLandingPadSizeResponse(null, null, null);
        
        Mockito.when(objectMapper.treeToValue(jsonNode, StationMaxLandingPadSizeResponse.class)).thenReturn(stationMaxLandingPadSizeResponse);
        
        underTest.listen(jsonNode);
        
        verify(receiveKafkaMessageUseCase, times(1)).receive(any(StationMaxLandingPadSizeResponse.class));
        
        
    }
    
}