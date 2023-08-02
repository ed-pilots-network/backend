package io.edpn.backend.exploration.adapter.kafka.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.exploration.application.port.incomming.ReceiveKafkaMessageUseCase;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.NavRouteMessage;
import io.edpn.backend.messageprocessorlib.infrastructure.kafka.processor.MessageProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;

@RequiredArgsConstructor
public class NavRouteV1MessageProcessor implements MessageProcessor<NavRouteMessage.V1> {

    private final ReceiveKafkaMessageUseCase<NavRouteMessage.V1> receiveNavRouteMessageUseCase;
    private final ObjectMapper objectMapper;

    @Override
    @KafkaListener(topics = "https___eddn.edcd.io_schemas_navroute_1", groupId = "explorationModule", containerFactory = "explorationModuleKafkaListenerContainerFactory")
    public void listen(JsonNode json) throws JsonProcessingException {
        handle(processJson(json));
    }

    @Override
    public void handle(NavRouteMessage.V1 message) {
        receiveNavRouteMessageUseCase.receive(message);
    }

    @Override
    public NavRouteMessage.V1 processJson(JsonNode json) throws JsonProcessingException {
        return objectMapper.treeToValue(json, NavRouteMessage.V1.class);
    }
}
