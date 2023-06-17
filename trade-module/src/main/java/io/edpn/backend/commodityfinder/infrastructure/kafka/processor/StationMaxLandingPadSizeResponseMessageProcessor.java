package io.edpn.backend.commodityfinder.infrastructure.kafka.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationMaxLandingPadSizeResponse;
import io.edpn.backend.commodityfinder.domain.usecase.ReceiveDataRequestResponseUseCase;
import io.edpn.backend.messageprocessorlib.infrastructure.kafka.processor.MessageProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;

@RequiredArgsConstructor
public class StationMaxLandingPadSizeResponseMessageProcessor implements MessageProcessor<StationMaxLandingPadSizeResponse> {

    private final ReceiveDataRequestResponseUseCase<StationMaxLandingPadSizeResponse> receiveDataRequestResponseUseCase;
    private final ObjectMapper objectMapper;

    @Override
    @KafkaListener(topics = "stationMaxLandingPadSizeResponse", groupId = "commodityFinder", containerFactory = "commodityFinderKafkaListenerContainerFactory")
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
