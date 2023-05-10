package io.edpn.backend.messageprocessor.fsssignaldiscoveredv1.infrastructure.kafka.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.messageprocessor.fsssignaldiscoveredv1.application.dto.eddn.FssSignalDiscoveredMessage;
import io.edpn.backend.messageprocessor.fsssignaldiscoveredv1.application.usecase.ReceiveFssSignalDiscoveredMessageUseCase;
import io.edpn.backend.messageprocessor.infrastructure.kafka.processor.EddnMessageProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.concurrent.Semaphore;

@RequiredArgsConstructor
public class FssSignalDiscoveredMessageProcessor implements EddnMessageProcessor<FssSignalDiscoveredMessage.V1> {

    private final ReceiveFssSignalDiscoveredMessageUseCase receiveFssSignalDiscoveredMessageUsecase;
    private final ObjectMapper objectMapper;
    private final Semaphore semaphore = new Semaphore(1); // Change the number of permits if needed

    @Override
    @KafkaListener(topics = "https___eddn.edcd.io_schemas_fsssignaldiscovered_1", groupId = "fsssignaldiscovered", containerFactory = "eddnFssSignalDiscoveredKafkaListenerContainerFactory")
    public void listen(JsonNode json) throws JsonProcessingException, InterruptedException {
        semaphore.acquire(); // Acquire a permit before processing the message
        try {
            handle(processJson(json));
        } finally {
            semaphore.release(); // Release the permit after processing is complete
        }
    }

    @Override
    public void handle(FssSignalDiscoveredMessage.V1 message) {
        receiveFssSignalDiscoveredMessageUsecase.receive(message);
    }

    @Override
    public FssSignalDiscoveredMessage.V1 processJson(JsonNode json) throws JsonProcessingException {
        return objectMapper.treeToValue(json, FssSignalDiscoveredMessage.V1.class);
    }
}
