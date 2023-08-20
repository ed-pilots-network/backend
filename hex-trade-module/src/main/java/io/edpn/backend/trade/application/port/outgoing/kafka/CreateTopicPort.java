package io.edpn.backend.trade.application.port.outgoing.kafka;

import java.util.concurrent.CompletableFuture;

public interface CreateTopicPort {
    CompletableFuture<Void> createTopicIfNotExists(String topicName);
}