package io.edpn.backend.commodityfinder.infrastructure.kafka.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.commodityfinder.domain.usecase.ReceiveApproachSettlementMessageUseCase;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.ApproachSettlement;
import io.edpn.backend.messageprocessorlib.infrastructure.kafka.processor.MessageProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ApproachSettlementV1MessageProcessor implements MessageProcessor<ApproachSettlement.V1> {

    private final ReceiveApproachSettlementMessageUseCase receiveApproachSettlementMessageUsecase;
    private final ObjectMapper objectMapper;

    @Override
    @KafkaListener(topics = "https___eddn.edcd.io_schemas_approachsettlement_1", groupId = "commodityFinder", containerFactory = "eddnCommodityFinderKafkaListenerContainerFactory")
    public void listen(JsonNode json) throws JsonProcessingException {
        handle(processJson(json));
    }

    @Override
    public void handle(ApproachSettlement.V1 message) {
        receiveApproachSettlementMessageUsecase.receive(message);
    }

    @Override
    public ApproachSettlement.V1 processJson(JsonNode json) throws JsonProcessingException {
        return objectMapper.treeToValue(json, ApproachSettlement.V1.class);
    }
}
