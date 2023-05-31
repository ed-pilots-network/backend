package io.edpn.backend.modulith.commodityfinder.infrastructure.kafka.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.edpn.backend.modulith.commodityfinder.domain.usecase.ReceiveCommodityMessageUseCase;
import io.edpn.backend.modulith.messageprocessorlib.application.dto.eddn.CommodityMessage;
import io.edpn.backend.modulith.messageprocessorlib.infrastructure.kafka.processor.EddnMessageProcessor;
import java.util.concurrent.Semaphore;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CommodityV3MessageProcessor implements EddnMessageProcessor<CommodityMessage.V3> {

    private final ReceiveCommodityMessageUseCase receiveCommodityMessageUsecase;
    private final ObjectMapper objectMapper;
    private final Semaphore semaphore = new Semaphore(1); // Change the number of permits if needed

    @Override
    @KafkaListener(topics = "https___eddn.edcd.io_schemas_commodity_3", groupId = "commodityFinder", containerFactory = "eddnCommodityFinderKafkaListenerContainerFactory")
    public void listen(JsonNode json) throws JsonProcessingException, InterruptedException {
        semaphore.acquire(); // Acquire a permit before processing the message
        try {
            handle(processJson(json));
        } finally {
            semaphore.release(); // Release the permit after processing is complete
        }
    }

    @Override
    public void handle(CommodityMessage.V3 message) {
        receiveCommodityMessageUsecase.receive(message);
    }

    @Override
    public CommodityMessage.V3 processJson(JsonNode json) throws JsonProcessingException {
        return objectMapper.treeToValue(json, CommodityMessage.V3.class);
    }
}
