package io.edpn.backend.commodityfinder.infrastructure.kafka.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.commodityfinder.domain.usecase.ReceiveCommodityMessageUseCase;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.CommodityMessage;
import io.edpn.backend.messageprocessorlib.infrastructure.kafka.processor.EddnMessageProcessor;
import io.edpn.backend.util.AutoCloseableSemaphore;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@RequiredArgsConstructor
@Component
public class CommodityV3MessageProcessor implements EddnMessageProcessor<CommodityMessage.V3> {

    private final ReceiveCommodityMessageUseCase receiveCommodityMessageUsecase;
    private final ObjectMapper objectMapper;
    private final Map<String, AutoCloseableSemaphore> semaphores;

    @Override
    @KafkaListener(topics = "https___eddn.edcd.io_schemas_commodity_3", groupId = "commodityFinder", containerFactory = "eddnCommodityFinderKafkaListenerContainerFactory")
    public void listen(JsonNode json) throws JsonProcessingException, InterruptedException {
        // Acquire the needed permits before processing the message, they auto release
        try (var marketDatum = semaphores.get("marketDatum")) {
            marketDatum.acquire();
            try (var commodity = semaphores.get("commodity")) {
                commodity.acquire();
                try (var station = semaphores.get("station")) {
                    station.acquire();
                    try (var system = semaphores.get("system")) {
                        system.acquire();
                        handle(processJson(json));
                    }
                }
            }
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
