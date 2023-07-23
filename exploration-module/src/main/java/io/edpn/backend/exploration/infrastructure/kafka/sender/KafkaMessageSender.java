package io.edpn.backend.exploration.infrastructure.kafka.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.exploration.domain.model.RequestDataMessage;
import io.edpn.backend.exploration.domain.repository.RequestDataMessageRepository;
import io.edpn.backend.exploration.infrastructure.kafka.KafkaTopicHandler;
import io.edpn.backend.exploration.infrastructure.persistence.entity.RequestDataMessageEntity;
import io.edpn.backend.exploration.infrastructure.persistence.mappers.entity.RequestDataMessageMapper;
import io.edpn.backend.exploration.infrastructure.persistence.mappers.mybatis.RequestDataMessageEntityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Objects;
import java.util.concurrent.Semaphore;

@RequiredArgsConstructor
@Slf4j
public class KafkaMessageSender implements RequestDataMessageRepository {

    private final ObjectMapper objectMapper;

    private final RequestDataMessageEntityMapper requestDataMessageEntityMapper;
    private final RequestDataMessageMapper requestDataMessageMapper;

    private final KafkaTopicHandler kafkaTopicHandler;
    private final KafkaTemplate<String, JsonNode> jsonNodekafkaTemplate;

    private final Semaphore semaphore = new Semaphore(1);

    @Override
    public void sendToKafka(RequestDataMessage requestDataMessage) {
        try {
            semaphore.acquire();
            RequestDataMessageEntity entity = requestDataMessageMapper.map(requestDataMessage);
            if (requestDataMessageEntityMapper.find(entity).isEmpty()) {
                requestDataMessageEntityMapper.insert(requestDataMessageMapper.map(requestDataMessage));
                sendPendingMessages();
            } else {
                log.debug("info request already queued");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Failed to acquire semaphore", e);
        } finally {
            semaphore.release();
        }
    }

    private void sendPendingMessages() {
        requestDataMessageEntityMapper.findNotSend()
                .forEach(requestDataMessageEntity -> {
                    try {
                        JsonNode jsonNode = objectMapper.readTree(requestDataMessageEntity.getMessage());

                        kafkaTopicHandler.createTopicIfNotExists(requestDataMessageEntity.getTopic())
                                .whenComplete((tName, kafkaTopicCreateException) -> {
                                    if (Objects.isNull(kafkaTopicCreateException)) {
                                        jsonNodekafkaTemplate.send(tName, jsonNode).whenComplete(
                                                (kafkaResult, kafkaSendException) -> {
                                                    if (Objects.nonNull(kafkaSendException)) {
                                                        //TODO handle kafka exception
                                                        log.error("could not send message to Kafka", kafkaSendException);
                                                        throw new RuntimeException(kafkaSendException.getMessage());
                                                    } else {
                                                        requestDataMessageEntity.setSend(true);
                                                        requestDataMessageEntityMapper.update(requestDataMessageEntity);
                                                    }
                                                }
                                        );
                                    } else {
                                        //TODO handle kafka exception
                                        log.error("could not create topic on Kafka", kafkaTopicCreateException);
                                        throw new RuntimeException(kafkaTopicCreateException.getMessage());
                                    }
                                });
                    } catch (JsonProcessingException e) {
                        log.error("could not convert message to json", e);
                        throw new RuntimeException(e);
                    }
                });
    }
}
