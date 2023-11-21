package io.edpn.backend.trade.adapter.kafka.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.CommodityMessage;
import io.edpn.backend.messageprocessorlib.infrastructure.kafka.processor.MessageProcessor;
import io.edpn.backend.trade.application.port.incomming.kafka.ReceiveKafkaMessageUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.MessageListener;

@RequiredArgsConstructor
@Slf4j
public class CommodityV3MessageProcessor implements MessageProcessor<CommodityMessage.V3>, MessageListener<String, JsonNode> {

    private final ReceiveKafkaMessageUseCase<CommodityMessage.V3> receiveCommodityMessageUsecase;
    private final ObjectMapper objectMapper;

    @Override
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

    @Override
    public void onMessage(ConsumerRecord<String, JsonNode> data) {
        try {
            this.listen(data.value());
        } catch (JsonProcessingException e) {
            log.error("Unable to process JSON", e);
            throw new RuntimeException(e);
        }
    }
}
