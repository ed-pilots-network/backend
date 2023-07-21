package io.edpn.backend.exploration.infrastructure.kafka.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.NavRouteMessage;
import io.edpn.backend.messageprocessorlib.infrastructure.kafka.processor.MessageProcessor;
import io.edpn.backend.exploration.domain.usecase.ReceiveNavRouteMessageUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;

@RequiredArgsConstructor
public class NavRouteV1MessageProcessor implements MessageProcessor<NavRouteMessage.V1> {

    private final ReceiveNavRouteMessageUseCase receiveNavRouteMessageUsecase;
    private final ObjectMapper objectMapper;

    @Override
    @KafkaListener(topics = "https___eddn.edcd.io_schemas_navroute_1", groupId = "explorationModule", containerFactory = "explorationModuleKafkaListenerContainerFactory")
    public void listen(JsonNode json) throws JsonProcessingException {
        handle(processJson(json));
    }

    @Override
    public void handle(NavRouteMessage.V1 message) {
        receiveNavRouteMessageUsecase.receive(message);
    }

    @Override
    public NavRouteMessage.V1 processJson(JsonNode json) throws JsonProcessingException {
        return objectMapper.treeToValue(json, NavRouteMessage.V1.class);
    }
}
