package io.edpn.backend.commodityfinder.infrastructure.kafka.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.commodityfinder.domain.usecase.ReceiveStationArrivalDistanceResponseUseCase;
import io.edpn.backend.messageprocessorlib.infrastructure.kafka.processor.MessageProcessor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;

@RequiredArgsConstructor
public class StationArrivalDistanceResponseMessageProcessor implements MessageProcessor<StationArrivalDistanceResponseMessageProcessor.StationArrivalDistanceResponse> {

    private final ReceiveStationArrivalDistanceResponseUseCase receiveStationArrivalDistanceResponseUseCase;
    private final ObjectMapper objectMapper;

    @Override
    @KafkaListener(topics = "stationArrivalDistanceDataResponse", groupId = "commodityFinder", containerFactory = "eddnCommodityFinderKafkaListenerContainerFactory")
    public void listen(JsonNode json) throws JsonProcessingException {
        handle(processJson(json));
    }

    @Override
    public void handle(StationArrivalDistanceResponse message) {
        receiveStationArrivalDistanceResponseUseCase.receive(message);
    }

    @Override
    public StationArrivalDistanceResponse processJson(JsonNode json) throws JsonProcessingException {
        return objectMapper.treeToValue(json, StationArrivalDistanceResponse.class);
    }

    @Data
    @NoArgsConstructor
    public static class StationArrivalDistanceResponse {
        private String stationName;
        private String systemName;
        private double arrivalDistance;
    }
}
