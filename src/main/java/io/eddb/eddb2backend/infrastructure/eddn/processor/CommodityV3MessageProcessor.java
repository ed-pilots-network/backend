package io.eddb.eddb2backend.infrastructure.eddn.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.eddb.eddb2backend.application.dto.eddn.CommodityMessage;

public class CommodityV3MessageProcessor implements EddnMessageProcessor<CommodityMessage.V3> {

    @Override
    public void handle(CommodityMessage.V3 message) {
        System.out.println(message); //TODO map to domain or dbEntity to save
    }

    @Override
    public CommodityMessage.V3 processJson(String json) throws JsonProcessingException {
        return new ObjectMapper().readValue(json, CommodityMessage.V3.class);
    }
}
