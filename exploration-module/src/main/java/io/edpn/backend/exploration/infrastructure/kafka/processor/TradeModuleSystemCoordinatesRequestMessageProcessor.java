package io.edpn.backend.exploration.infrastructure.kafka.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.exploration.domain.usecase.ReceiveDataRequestUseCase;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemDataRequest;
import io.edpn.backend.messageprocessorlib.infrastructure.kafka.processor.MessageProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;

@RequiredArgsConstructor
public class TradeModuleSystemCoordinatesRequestMessageProcessor implements MessageProcessor<SystemDataRequest> {

    private final ReceiveDataRequestUseCase<SystemDataRequest> receiveDataRequestUseCase;
    private final ObjectMapper objectMapper;

    @Override
    @KafkaListener(topics = "systemCoordinatesDataRequest", groupId = "explorationModule", containerFactory = "explorationModuleKafkaListenerContainerFactory")
    public void listen(JsonNode json) throws JsonProcessingException {
        handle(processJson(json));
    }

    @Override
    public void handle(SystemDataRequest message) {
        receiveDataRequestUseCase.receive(message);
    }

    @Override
    public SystemDataRequest processJson(JsonNode json) throws JsonProcessingException {
        return objectMapper.treeToValue(json, SystemDataRequest.class);
    }
}
