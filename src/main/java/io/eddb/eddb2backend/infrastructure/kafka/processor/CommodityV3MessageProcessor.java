package io.eddb.eddb2backend.infrastructure.kafka.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.eddb.eddb2backend.application.dto.eddn.CommodityMessage;
import org.springframework.kafka.annotation.KafkaListener;

public class CommodityV3MessageProcessor implements EddnMessageProcessor<CommodityMessage.V3> {

    @Override
    @KafkaListener(topics = "https___eddn.edcd.io_schemas_commodity_3", groupId = "commodity", containerFactory = "eddnCommodityKafkaListenerContainerFactory")
    public void listen(JsonNode json) throws JsonProcessingException {
        handle(processJson(json));
    }

    @Override
    public void handle(CommodityMessage.V3 message) {
        System.out.println(message); //TODO map to domain or dbEntity to save
    }

    @Override
    public CommodityMessage.V3 processJson(JsonNode json) throws JsonProcessingException {
        return new ObjectMapper().treeToValue(json, CommodityMessage.V3.class);
    }
}
