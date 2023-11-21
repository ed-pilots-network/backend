package io.edpn.backend.trade.adapter.kafka.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemCoordinatesResponse;
import io.edpn.backend.messageprocessorlib.infrastructure.kafka.processor.MessageProcessor;
import io.edpn.backend.trade.application.port.incomming.kafka.ReceiveKafkaMessageUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.MessageListener;

@RequiredArgsConstructor
@Slf4j
public class SystemCoordinatesResponseMessageProcessor implements MessageProcessor<SystemCoordinatesResponse>, MessageListener<String, JsonNode> {

    private final ReceiveKafkaMessageUseCase<SystemCoordinatesResponse> receiveDataRequestResponseUseCase;
    private final ObjectMapper objectMapper;

    @Override
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
