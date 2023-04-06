package io.eddb.eddb2backend.infrastructure.external;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

public class EddnMessageHandler implements MessageHandler {
    @Override
    public void handleMessage(Message<?> message) throws MessagingException {

        //TODO process message to DB

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
