package io.edpn.backend.trade.infrastructure.kafka.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemCoordinatesResponse;
import io.edpn.backend.trade.domain.usecase.ReceiveDataRequestResponseUseCase;
import io.edpn.backend.messageprocessorlib.infrastructure.kafka.processor.MessageProcessor;
import io.edpn.backend.util.Topic;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;

@RequiredArgsConstructor
public class SystemCoordinatesResponseMessageProcessor implements MessageProcessor<SystemCoordinatesResponse> {

    private final ReceiveDataRequestResponseUseCase<SystemCoordinatesResponse> receiveDataRequestResponseUseCase;
    private final ObjectMapper objectMapper;

    @Override
    @KafkaListener(topics = "trade_systemCoordinatesResponse", groupId = "tradeModule", containerFactory = "tradeModuleKafkaListenerContainerFactory")
    public void listen(JsonNode json) throws JsonProcessingException {
        handle(processJson(json));
    }

    @Override
    public void handle(SystemCoordinatesResponse message) {
        receiveDataRequestResponseUseCase.receive(message);
    }

    @Override
    public SystemCoordinatesResponse processJson(JsonNode json) throws JsonProcessingException {
        return objectMapper.treeToValue(json, SystemCoordinatesResponse.class);
    }
}
