package io.edpn.backend.exploration.adapter.kafka.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.exploration.application.domain.KafkaMessage;
import io.edpn.backend.exploration.application.port.outgoing.CreateTopicPort;
import io.edpn.backend.exploration.application.port.outgoing.SendKafkaMessagePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Slf4j
public class KafkaMessageSender implements SendKafkaMessagePort {

    private final CreateTopicPort createTopicPort;
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, JsonNode> jsonNodekafkaTemplate;


    @Override
    public Boolean send(KafkaMessage kafkaMessage) {
        return createTopicPort.createTopicIfNotExists(kafkaMessage.topic())
                .thenCompose(v -> {
                    try {
                        JsonNode jsonNodeMessage = objectMapper.readTree(kafkaMessage.message());
                        jsonNodekafkaTemplate.send(kafkaMessage.topic(), jsonNodeMessage);
                        return CompletableFuture.completedFuture(true);
                    } catch (JsonProcessingException e) {
                        log.error("Unable to send message to Kafka", e);
                        return CompletableFuture.completedFuture(false);
                    }
                }).exceptionally(ex -> {
                    log.error("Error while creating topic or sending message", ex);
                    return false;
                }).join();
    }
}
