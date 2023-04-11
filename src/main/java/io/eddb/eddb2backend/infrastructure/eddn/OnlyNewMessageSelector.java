package io.eddb.eddb2backend.infrastructure.eddn;

import io.eddb.eddb2backend.domain.util.LRUSetCache;
import java.util.Objects;
import org.springframework.integration.core.MessageSelector;
import org.springframework.messaging.Message;

public class OnlyNewMessageSelector implements MessageSelector {
    private final LRUSetCache<String> receivedMessageIds = new LRUSetCache<>(1000);

    @Override
    public boolean accept(Message<?> message) {
        String messageId = Objects.requireNonNull(message.getHeaders().getId()).toString();

        if (receivedMessageIds.contains(messageId)) {
            return false;
        } else {
            receivedMessageIds.add(messageId);
            return true;
        }
    }
}
