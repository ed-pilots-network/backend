package io.eddb.eddb2backend.infrastructure.eddn;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.eddb.eddb2backend.domain.exception.UnsupportedSchemaException;
import io.eddb.eddb2backend.infrastructure.eddn.processor.CommodityV3MessageProcessor;
import io.eddb.eddb2backend.infrastructure.eddn.processor.EddnMessageProcessor;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.retry.support.RetryTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

@RequiredArgsConstructor
public class EddnMessageHandler implements MessageHandler {

    private final TaskExecutor taskExecutor;
    private final RetryTemplate retryTemplate;
    private final ObjectMapper objectMapper;
    @Getter(AccessLevel.PRIVATE)
    private final CommodityV3MessageProcessor commodityV3MessageProcessor;
    private final Map<String, EddnMessageProcessor<?>> schemaRefToProcessorMap = Map.of(
            "https://eddn.edcd.io/schemas/commodity/3", getCommodityV3MessageProcessor()
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
            String schemaRef = objectMapper.readTree(json).get("$schemaRef").asText();

            Optional.ofNullable(schemaRefToProcessorMap.get(schemaRef))
                    .orElseThrow(() -> new UnsupportedSchemaException(schemaRef))
                    .handle(json);
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
}
