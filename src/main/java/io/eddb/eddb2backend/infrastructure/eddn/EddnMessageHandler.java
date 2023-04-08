package io.eddb.eddb2backend.infrastructure.eddn;

import lombok.RequiredArgsConstructor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

@RequiredArgsConstructor
public class EddnMessageHandler implements MessageHandler {

    private final TaskExecutor taskExecutor;

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        while (!Thread.currentThread().isInterrupted()) {
            taskExecutor.execute(() -> processMessage(message));
        }
    }

    private void processMessage(Message<?> message) throws MessagingException {
        /* TODO create and inject MessageHandlers for each relevant schema type to transform the messages to domain types.
         * then pass these domain types to persistence layer to save
         */

        byte[] output = new byte[256 * 1024];
        byte[] payload = (byte[]) message.getPayload();
        Inflater inflater = new Inflater();
        inflater.setInput(payload);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(payload.length);
        try {
            int outputLength = inflater.inflate(output);
            String outputString = new String(output, 0, outputLength, StandardCharsets.UTF_8);
            System.out.println(outputString);
        } catch (DataFormatException e) {
            e.printStackTrace();
        }
    }

}
