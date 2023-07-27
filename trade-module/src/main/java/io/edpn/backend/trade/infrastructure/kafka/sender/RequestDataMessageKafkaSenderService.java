package io.edpn.backend.trade.infrastructure.kafka.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.trade.domain.model.RequestDataMessage;
import io.edpn.backend.trade.domain.repository.RequestDataMessageRepository;
import io.edpn.backend.trade.domain.service.KafkaSenderService;
import io.edpn.backend.trade.infrastructure.kafka.KafkaTopicHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
public class RequestDataMessageKafkaSenderService implements KafkaSenderService<RequestDataMessage> {

    private final ObjectMapper objectMapper;

    private final RequestDataMessageRepository requestDataMessageRepository;

    private final KafkaTopicHandler kafkaTopicHandler;
    private final KafkaTemplate<String, JsonNode> jsonNodekafkaTemplate;

    @Override
    public synchronized void sendToKafka(RequestDataMessage requestDataMessage) {
        if (requestDataMessageRepository.find(requestDataMessage).isEmpty()) {
            requestDataMessageRepository.create(requestDataMessage);
        } else {
            log.debug("info request already queued");
        }

        sendPendingMessages();
    }

    private synchronized void sendPendingMessages() {
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
