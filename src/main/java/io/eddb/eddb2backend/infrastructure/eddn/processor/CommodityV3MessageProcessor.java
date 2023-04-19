package io.eddb.eddb2backend.infrastructure.eddn.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.eddb.eddb2backend.application.dto.eddn.CommodityMessage;
import io.eddb.eddb2backend.application.usecase.ReceiveCommodityMessageUsecase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommodityV3MessageProcessor implements EddnMessageProcessor<CommodityMessage.V3> {

    private final ReceiveCommodityMessageUsecase receiveCommodityMessageUsecase;

    @Override
    public void handle(CommodityMessage.V3 message) {
        System.out.println(message);
        receiveCommodityMessageUsecase.receive(message);
    }

    @Override
    public CommodityMessage.V3 processJson(String json) throws JsonProcessingException {
        return new ObjectMapper().readValue(json, CommodityMessage.V3.class);
    }
}
