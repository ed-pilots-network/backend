package io.edpn.backend.trade.adapter.kafka.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemEliteIdResponse;
import io.edpn.backend.messageprocessorlib.infrastructure.kafka.processor.MessageProcessor;
import io.edpn.backend.trade.application.port.incomming.ReceiveKafkaMessageUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;

@RequiredArgsConstructor
public class SystemEliteIdResponseMessageProcessor implements MessageProcessor<SystemEliteIdResponse> {

    private final ReceiveKafkaMessageUseCase<SystemEliteIdResponse> receiveDataRequestResponseUseCase;
    private final ObjectMapper objectMapper;

    @Override
    @KafkaListener(topics = "tradeModuleSystemEliteIdResponse", groupId = "tradeModule", containerFactory = "tradeModuleKafkaListenerContainerFactory")
    public void listen(JsonNode json) throws JsonProcessingException {
        handle(processJson(json));
    }

    @Override
    public void handle(SystemEliteIdResponse message) {
        receiveDataRequestResponseUseCase.receive(message);
    }

    @Override
    public SystemEliteIdResponse processJson(JsonNode json) throws JsonProcessingException {
        return objectMapper.treeToValue(json, SystemEliteIdResponse.class);
    }
}
