package io.edpn.backend.exploration.adapter.kafka.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.edpn.backend.exploration.application.port.incomming.ReceiveKafkaMessageUseCase;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationDataRequest;
import io.edpn.backend.messageprocessorlib.infrastructure.kafka.processor.MessageProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.MessageListener;

@RequiredArgsConstructor
@Slf4j
public class StationDataRequestMessageProcessor implements MessageProcessor<StationDataRequest>, MessageListener<String, JsonNode> {

    private final ReceiveKafkaMessageUseCase<StationDataRequest> receiveStationDataRequestUseCase;
    private final ObjectMapper objectMapper;

    @Override
    public void listen(JsonNode json) throws JsonProcessingException {
        handle(processJson(json));
    }

    @Override
    public void handle(StationDataRequest message) {
        receiveStationDataRequestUseCase.receive(message);
    }

    @Override
    public StationDataRequest processJson(JsonNode json) throws JsonProcessingException {
        if (json.isObject() && json.has("requestingModule")) {
            var requestingModule = json.get("requestingModule").asText().toUpperCase();
            ((ObjectNode) json).put("requestingModule", requestingModule);
        }

        return objectMapper.treeToValue(json, StationDataRequest.class);
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
