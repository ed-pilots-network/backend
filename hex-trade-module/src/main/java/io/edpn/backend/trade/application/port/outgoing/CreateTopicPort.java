package io.edpn.backend.trade.application.port.outgoing;

import java.util.concurrent.CompletableFuture;

public interface CreateTopicPort {
    CompletableFuture<Void> createTopicIfNotExists(String topicName);
}