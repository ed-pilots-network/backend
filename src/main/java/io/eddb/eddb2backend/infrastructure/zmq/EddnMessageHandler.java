package io.eddb.eddb2backend.infrastructure.zmq;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.eddb.eddb2backend.domain.exception.UnsupportedSchemaException;
import io.eddb.eddb2backend.infrastructure.kafka.KafkaTopicHandler;
import io.eddb.eddb2backend.infrastructure.kafka.processor.CommodityV3MessageProcessor;
import io.eddb.eddb2backend.infrastructure.kafka.processor.EddnMessageProcessor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.retry.support.RetryTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

@RequiredArgsConstructor
public class EddnMessageHandler implements MessageHandler {

    private final TaskExecutor taskExecutor;
    private final RetryTemplate retryTemplate;
    private final ObjectMapper objectMapper;
    private final KafkaTopicHandler kafkaTopicHandler;
    private final KafkaTemplate<String, JsonNode> jsonNodekafkaTemplate;
    private final Map<String, EddnMessageProcessor<?>> schemaRefToProcessorMap = Map.of(
            "https://eddn.edcd.io/schemas/commodity/3", new CommodityV3MessageProcessor() //TODO maybe add as beans?
    );

    @Override
    public void handleMessage(@NonNull Message<?> message) throws MessagingException {
        try {
            retryTemplate.execute(retryContext -> {
                taskExecutor.execute(() -> processMessage(message));
                return null;
            });
        } catch (Throwable e) {
            System.err.println("message could not be handled in time, dropping");
            e.printStackTrace(System.err);
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

            kafkaTopicHandler.createTopicIfNotExists(sanitizedTopicName)
                    .whenComplete((topicName, kafkaTopicCreateException) -> {
                        if (Objects.isNull(kafkaTopicCreateException)) {
                            jsonNodekafkaTemplate.send(topicName, jsonNode).whenComplete(
                                    (kafkaResult, kafkaSendException) -> {
                                        if (Objects.nonNull(kafkaSendException)) {
                                            //TODO log and handle kafka exception
                                            throw new RuntimeException(kafkaSendException.getMessage());
                                        }
                                    }
                            );
                        } else {
                            //TODO log and handle kafka exception
                            throw new RuntimeException(kafkaTopicCreateException.getMessage());
                        }
                    });

        } catch (DataFormatException dfe) {
            dfe.printStackTrace();
            //TODO add some damn logging framework!!!
        } catch (IOException ioe) {
            ioe.printStackTrace();
            //TODO add some damn logging framework!!!
        } catch (UnsupportedSchemaException use) {
            //noop
            System.out.println(use.getMessage());
        }
    }

    public String sanitizeTopicName(String schemaRef) {
        return schemaRef.replaceAll("[^A-Za-z0-9._\\-]", "_");
    }
}
