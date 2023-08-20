package io.edpn.backend.trade.adapter.kafka.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationArrivalDistanceResponse;
import io.edpn.backend.messageprocessorlib.infrastructure.kafka.processor.MessageProcessor;
import io.edpn.backend.trade.application.port.incomming.kafka.ReceiveKafkaMessageUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;

@RequiredArgsConstructor
public class StationArrivalDistanceResponseMessageProcessor implements MessageProcessor<StationArrivalDistanceResponse> {

    private final ReceiveKafkaMessageUseCase<StationArrivalDistanceResponse> receiveDataRequestResponseUseCase;
    private final ObjectMapper objectMapper;

    @Override
    @KafkaListener(topics = "tradeModuleStationArrivalDistanceDataResponse", groupId = "tradeModule", containerFactory = "tradeModuleKafkaListenerContainerFactory")
    public void listen(JsonNode json) throws JsonProcessingException {
        handle(processJson(json));
    }

    @Override
    public void handle(StationArrivalDistanceResponse message) {
        receiveDataRequestResponseUseCase.receive(message);
    }

    @Override
    public StationArrivalDistanceResponse processJson(JsonNode json) throws JsonProcessingException {
        return objectMapper.treeToValue(json, StationArrivalDistanceResponse.class);
    }
}
