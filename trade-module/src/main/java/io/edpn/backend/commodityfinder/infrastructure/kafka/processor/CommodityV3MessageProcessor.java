package io.edpn.backend.commodityfinder.infrastructure.kafka.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.commodityfinder.domain.usecase.ReceiveCommodityMessageUseCase;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.CommodityMessage;
import io.edpn.backend.messageprocessorlib.infrastructure.kafka.processor.MessageProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;

@RequiredArgsConstructor
public class CommodityV3MessageProcessor implements MessageProcessor<CommodityMessage.V3> {

    private final ReceiveCommodityMessageUseCase receiveCommodityMessageUsecase;
    private final ObjectMapper objectMapper;

    @Override
    @KafkaListener(topics = "https___eddn.edcd.io_schemas_commodity_3", groupId = "tradeModule", containerFactory = "tradeModuleKafkaListenerContainerFactory")
    public void listen(JsonNode json) throws JsonProcessingException {
        handle(processJson(json));
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
