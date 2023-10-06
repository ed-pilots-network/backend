package io.edpn.backend.exploration.adapter.kafka.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.exploration.application.port.incomming.ReceiveKafkaMessageUseCase;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.ScanBaryCentreMessage;
import io.edpn.backend.messageprocessorlib.infrastructure.kafka.processor.MessageProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;

@RequiredArgsConstructor
public class ScanBaryCentreV1MessageProcessor implements MessageProcessor<ScanBaryCentreMessage.V1> {

    private final ReceiveKafkaMessageUseCase<ScanBaryCentreMessage.V1> receiveScanBaryCentreMessageUseCase;
    private final ObjectMapper objectMapper;

    //TODO: Change topic
    @Override
    @KafkaListener(topics = "https___eddn.edcd.io_schemas_scanbarycentre_1", groupId = "explorationModule", containerFactory = "explorationModuleKafkaListenerContainerFactory")
    public void listen(JsonNode json) throws JsonProcessingException {
        handle(processJson(json));
    }

    @Override
    public void handle(ScanBaryCentreMessage.V1 message) {
        receiveScanBaryCentreMessageUseCase.receive(message);
    }

    @Override
    public ScanBaryCentreMessage.V1 processJson(JsonNode json) throws JsonProcessingException {
        return objectMapper.treeToValue(json, ScanBaryCentreMessage.V1.class);
    }
}
