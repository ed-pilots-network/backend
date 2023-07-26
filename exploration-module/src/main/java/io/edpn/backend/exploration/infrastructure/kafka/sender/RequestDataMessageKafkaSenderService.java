package io.edpn.backend.exploration.infrastructure.kafka.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.exploration.domain.model.RequestDataMessage;
import io.edpn.backend.exploration.domain.repository.RequestDataMessageRepository;
import io.edpn.backend.exploration.domain.service.KafkaSenderService;
import io.edpn.backend.exploration.infrastructure.kafka.KafkaTopicHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Objects;
import java.util.concurrent.Semaphore;

@RequiredArgsConstructor
@Slf4j
public class RequestDataMessageKafkaSenderService implements KafkaSenderService<RequestDataMessage> {

    private final ObjectMapper objectMapper;

    private final RequestDataMessageRepository requestDataMessageRepository;

    private final KafkaTopicHandler kafkaTopicHandler;
    private final KafkaTemplate<String, JsonNode> jsonNodekafkaTemplate;

    private final Semaphore semaphore = new Semaphore(1);

    @Override
    public void sendToKafka(RequestDataMessage requestDataMessage) {
        try {
            semaphore.acquire();
            if (requestDataMessageRepository.find(requestDataMessage).isEmpty()) {
                requestDataMessageRepository.create(requestDataMessage);
            } else {
                log.debug("info request already queued");
            }

            sendPendingMessages();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Failed to acquire semaphore", e);
        } finally {
            semaphore.release();
        }
    }

    private void sendPendingMessages() {
        requestDataMessageRepository.findNotSend()
                .forEach(requestDataMessage ->
                        kafkaTopicHandler.createTopicIfNotExists(requestDataMessage.getTopic())
                                .whenComplete((tName, kafkaTopicCreateException) -> {
                                    if (Objects.isNull(kafkaTopicCreateException)) {
                                        try {
                                            JsonNode jsonNode = objectMapper.readTree(requestDataMessage.getMessage());
                                            jsonNodekafkaTemplate.send(tName, jsonNode).whenComplete(
                                                    (kafkaResult, kafkaSendException) -> {
                                                        if (Objects.nonNull(kafkaSendException)) {
                                                            log.error("could not send message to Kafka", kafkaSendException);
                                                            throw new RuntimeException(kafkaSendException.getMessage());
                                                        } else {
                                                            requestDataMessageRepository.setSend(requestDataMessage);
                                                        }
                                                    });
                                        } catch (JsonProcessingException e) {
                                            log.error("could not convert message to json", e);
                                            throw new RuntimeException(e);
                                        }
                                    } else {
                                        log.error("could not create topic on Kafka", kafkaTopicCreateException);
                                        throw new RuntimeException(kafkaTopicCreateException.getMessage());
                                    }
                                }));
    }
}
