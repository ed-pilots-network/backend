package io.edpn.backend.messageprocessor.navroutev1.infrastructure.kafka.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.messageprocessor.navroutev1.application.dto.eddn.NavRouteMessage;
import io.edpn.backend.messageprocessor.navroutev1.application.usecase.ReceiveNavRouteMessageUseCase;
import io.edpn.backend.messageprocessor.infrastructure.kafka.processor.EddnMessageProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.concurrent.Semaphore;

@RequiredArgsConstructor
public class NavRouteMessageProcessor implements EddnMessageProcessor<NavRouteMessage.V1> {

    private final ReceiveNavRouteMessageUseCase receiveCommodityMessageUsecase;
    private final ObjectMapper objectMapper;
    private final Semaphore semaphore = new Semaphore(1); // Change the number of permits if needed

    @Override
    @KafkaListener(topics = "https___eddn.edcd.io_schemas_navroute_1", groupId = "navroute", containerFactory = "eddnNavRouteKafkaListenerContainerFactory")
    public void listen(JsonNode json) throws JsonProcessingException, InterruptedException {
        semaphore.acquire(); // Acquire a permit before processing the message
        try {
            handle(processJson(json));
        } finally {
            semaphore.release(); // Release the permit after processing is complete
        }
    }

    @Override
    public void handle(NavRouteMessage.V1 message) {
        receiveCommodityMessageUsecase.receive(message);
    }

    @Override
    public NavRouteMessage.V1 processJson(JsonNode json) throws JsonProcessingException {
        return objectMapper.treeToValue(json, NavRouteMessage.V1.class);
    }
}
