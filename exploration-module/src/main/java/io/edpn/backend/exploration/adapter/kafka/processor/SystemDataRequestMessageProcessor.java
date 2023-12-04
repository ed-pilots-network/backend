package io.edpn.backend.exploration.adapter.kafka.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.edpn.backend.exploration.application.port.incomming.ReceiveKafkaMessageUseCase;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemDataRequest;
import io.edpn.backend.messageprocessorlib.infrastructure.kafka.processor.MessageProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.MessageListener;

@RequiredArgsConstructor
@Slf4j
public class SystemDataRequestMessageProcessor implements MessageProcessor<SystemDataRequest>, MessageListener<String, JsonNode> {

    private final ReceiveKafkaMessageUseCase<SystemDataRequest> receiveSystemDataRequestUseCase;
    private final ObjectMapper objectMapper;

    @Override
    public void listen(JsonNode json) throws JsonProcessingException {
        handle(processJson(json));
    }

    @Override
    public void handle(SystemDataRequest message) {
        receiveSystemDataRequestUseCase.receive(message);
    }

    @Override
    public SystemDataRequest processJson(JsonNode json) throws JsonProcessingException {
        if (json.isObject() && json.has("requestingModule")) {
            var requestingModule = json.get("requestingModule").asText().toUpperCase();
            ((ObjectNode) json).put("requestingModule", requestingModule);
        }

        return objectMapper.treeToValue(json, SystemDataRequest.class);
    }

    @Override
    public void onMessage(ConsumerRecord<String, JsonNode> data) {
        try {
            this.listen(data.value());
        } catch (JsonProcessingException e) {
            log.error("Unable to process JSON", e);
            throw new RuntimeException(e);
        }
    }
}
