package io.edpn.backend.trade.adapter.kafka.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationMaxLandingPadSizeResponse;
import io.edpn.backend.messageprocessorlib.infrastructure.kafka.processor.MessageProcessor;
import io.edpn.backend.trade.application.port.incomming.ReceiveKafkaMessageUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;

@RequiredArgsConstructor
public class StationMaxLandingPadSizeResponseMessageProcessor implements MessageProcessor<StationMaxLandingPadSizeResponse> {

    private final ReceiveKafkaMessageUseCase<StationMaxLandingPadSizeResponse> receiveDataRequestResponseUseCase;
    private final ObjectMapper objectMapper;

    @Override
    @KafkaListener(topics = "tradeModuleStationMaxLandingPadSizeResponse", groupId = "tradeModule", containerFactory = "tradeModuleKafkaListenerContainerFactory")
    public void listen(JsonNode json) throws JsonProcessingException {
        handle(processJson(json));
    }

    @Override
    public void handle(StationMaxLandingPadSizeResponse message) {
        receiveDataRequestResponseUseCase.receive(message);
    }

    @Override
    public StationMaxLandingPadSizeResponse processJson(JsonNode json) throws JsonProcessingException {
        return objectMapper.treeToValue(json, StationMaxLandingPadSizeResponse.class);
    }
}
