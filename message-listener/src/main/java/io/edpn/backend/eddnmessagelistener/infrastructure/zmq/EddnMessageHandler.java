package io.edpn.backend.eddnmessagelistener.infrastructure.zmq;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.eddnmessagelistener.domain.exception.UnsupportedSchemaException;
import io.edpn.backend.eddnmessagelistener.infrastructure.kafka.KafkaTopicHandler;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.retry.support.RetryTemplate;

@RequiredArgsConstructor
public class EddnMessageHandler implements MessageHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(EddnMessageHandler.class);

    private final TaskExecutor taskExecutor;
    private final RetryTemplate retryTemplate;
    private final ObjectMapper objectMapper;
    private final KafkaTopicHandler kafkaTopicHandler;
    private final KafkaTemplate<String, JsonNode> jsonNodekafkaTemplate;
    private final MongoTemplate mongoTemplate;

    @Override
    public void handleMessage(@NonNull Message<?> message) throws MessagingException {
        try {
            retryTemplate.execute(retryContext -> {
                taskExecutor.execute(() -> processMessage(message));
                return null;
            });
        } catch (Throwable e) {
            LOGGER.error("could not handle message in time, dropping it", e);
        }
    }

    private void processMessage(Message<?> message) throws MessagingException {
        try {
            byte[] output = new byte[256 * 1024];
            byte[] payload = (byte[]) message.getPayload();
            Inflater inflater = new Inflater();
            inflater.setInput(payload);
            String json = new String(output, 0, inflater.inflate(output), StandardCharsets.UTF_8);
            JsonNode jsonNode = objectMapper.readTree(json);
            String schemaRef = jsonNode.get("$schemaRef").asText();
            String sanitizedTopicName = sanitizeTopicName(schemaRef);

            saveToMongo(json, schemaRef);
            sendToKafka(jsonNode, sanitizedTopicName);
        } catch (DataFormatException | IOException e) {
            LOGGER.error("Error processing EDDN message", e);
        } catch (UnsupportedSchemaException use) {
            //noop
            LOGGER.trace("Schema is unsupported, we will not process", use);
        }
    }

    private void saveToMongo(String json, String schemaRef) {
        Document document = Document.parse(json);
        mongoTemplate.insert(document, schemaRef);
    }

    private void sendToKafka(JsonNode jsonNode, String sanitizedTopicName) {
        kafkaTopicHandler.createTopicIfNotExists(sanitizedTopicName)
                .whenComplete((topicName, kafkaTopicCreateException) -> {
                    if (Objects.isNull(kafkaTopicCreateException)) {
                        jsonNodekafkaTemplate.send(topicName, jsonNode).whenComplete(
                                (kafkaResult, kafkaSendException) -> {
                                    if (Objects.nonNull(kafkaSendException)) {
                                        //TODO handle kafka exception
                                        LOGGER.error("could not send message to Kafka", kafkaSendException);
                                        throw new RuntimeException(kafkaSendException.getMessage());
                                    }
                                }
                        );
                    } else {
                        //TODO handle kafka exception
                        LOGGER.error("could not create topic on Kafka", kafkaTopicCreateException);
                        throw new RuntimeException(kafkaTopicCreateException.getMessage());
                    }
                });
    }

    public String sanitizeTopicName(String schemaRef) {
        return schemaRef.replaceAll("[^A-Za-z0-9._\\-]", "_");
    }
}
