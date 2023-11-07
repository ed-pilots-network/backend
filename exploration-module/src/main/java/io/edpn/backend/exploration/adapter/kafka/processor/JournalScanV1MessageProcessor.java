package io.edpn.backend.exploration.adapter.kafka.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.exploration.application.port.incomming.ReceiveKafkaMessageUseCase;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.journal.ScanMessage;
import io.edpn.backend.messageprocessorlib.infrastructure.kafka.processor.MessageProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;

@RequiredArgsConstructor
public class JournalScanV1MessageProcessor implements MessageProcessor<ScanMessage.V1> {

    private final ReceiveKafkaMessageUseCase<ScanMessage.V1> receiveJournalScanMessageUseCase;
    private final ObjectMapper objectMapper;
    
    @Override
    @KafkaListener(topics = "https___eddn.edcd.io_schemas_journal_1_scan", groupId = "explorationModule", containerFactory = "explorationModuleKafkaListenerContainerFactory")
    public void listen(JsonNode json) throws JsonProcessingException {
        handle(processJson(json));
    }

    @Override
    public void handle(ScanMessage.V1 message) {
        receiveJournalScanMessageUseCase.receive(message);
    }

    @Override
    public ScanMessage.V1 processJson(JsonNode json) throws JsonProcessingException {
        return objectMapper.treeToValue(json, ScanMessage.V1.class);
    }
}
