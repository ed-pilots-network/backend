package io.edpn.backend.exploration.application.port.outgoing.topic;

import java.util.concurrent.CompletableFuture;

public interface CreateTopicPort {
    CompletableFuture<Void> createTopicIfNotExists(String topicName);
}
