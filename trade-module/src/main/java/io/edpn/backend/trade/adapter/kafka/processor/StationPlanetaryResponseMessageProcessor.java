package io.edpn.backend.trade.adapter.kafka.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.trade.application.domain.intermodulecommunication.StationPlanetaryResponse;
import io.edpn.backend.messageprocessorlib.infrastructure.kafka.processor.MessageProcessor;
import io.edpn.backend.trade.application.port.incomming.kafka.ReceiveKafkaMessageUseCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StationPlanetaryResponseMessageProcessor implements MessageProcessor<StationPlanetaryResponse> {

    private final ReceiveKafkaMessageUseCase<StationPlanetaryResponse> receiveDataRequestResponseUseCase;
    private final ObjectMapper objectMapper;

    @Override
    public void listen(JsonNode json) throws JsonProcessingException {
        handle(processJson(json));
    }

    @Override
    public void handle(StationPlanetaryResponse message) {
        receiveDataRequestResponseUseCase.receive(message);
    }

    @Override
    public StationPlanetaryResponse processJson(JsonNode json) throws JsonProcessingException {
        return objectMapper.treeToValue(json, StationPlanetaryResponse.class);
    }
}
