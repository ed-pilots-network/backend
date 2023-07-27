package io.edpn.backend.trade.infrastructure.kafka.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.ApproachSettlementMessage;
import io.edpn.backend.messageprocessorlib.infrastructure.kafka.processor.MessageProcessor;
import io.edpn.backend.trade.domain.usecase.ReceiveApproachSettlementMessageUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;

@RequiredArgsConstructor
public class ApproachSettelementMessageProcessor implements MessageProcessor<ApproachSettlementMessage.V1> {

    private final ReceiveApproachSettlementMessageUseCase receiveApproachSettlementMessageUseCase;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "https___eddn.edcd.io_schemas_commodity_3", groupId = "tradeModule",containerFactory = "tradeModuleKafkaListenerContainerFactory")
    @Override
    public void listen(JsonNode jsonNode) throws JsonProcessingException {
        handle(processJson(jsonNode));
    }

    @Override
    public void handle(ApproachSettlementMessage.V1 message) {
        receiveApproachSettlementMessageUseCase.receive(message);
    }

    @Override
    public ApproachSettlementMessage.V1 processJson(JsonNode jsonNode) throws JsonProcessingException {
        return objectMapper.treeToValue(jsonNode, ApproachSettlementMessage.V1.class);
    }

}
