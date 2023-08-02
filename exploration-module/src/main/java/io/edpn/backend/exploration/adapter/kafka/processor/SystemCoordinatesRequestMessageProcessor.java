package io.edpn.backend.exploration.adapter.kafka.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.exploration.application.port.incomming.ReceiveKafkaMessageUseCase;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemDataRequest;
import io.edpn.backend.messageprocessorlib.infrastructure.kafka.processor.MessageProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;

@RequiredArgsConstructor
public class SystemCoordinatesRequestMessageProcessor implements MessageProcessor<SystemDataRequest> {

    private final ReceiveKafkaMessageUseCase<SystemDataRequest> receiveSystemDataRequestUseCase;
    private final ObjectMapper objectMapper;

    @Override
    @KafkaListener(topics = "trade_systemCoordinatesDataRequest", groupId = "explorationModule", containerFactory = "explorationModuleKafkaListenerContainerFactory")
    public void listen(JsonNode json) throws JsonProcessingException {
        handle(processJson(json));
    }

    @Override
    public void handle(SystemDataRequest message) {
        receiveSystemDataRequestUseCase.receive(message);
    }

    @Override
    public SystemDataRequest processJson(JsonNode json) throws JsonProcessingException {
        return objectMapper.treeToValue(json, SystemDataRequest.class);
    }
}
