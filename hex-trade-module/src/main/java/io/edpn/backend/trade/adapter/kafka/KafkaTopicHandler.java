package io.edpn.backend.trade.adapter.kafka;

import io.edpn.backend.trade.application.port.outgoing.kafka.CreateTopicPort;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
public class KafkaTopicHandler implements CreateTopicPort {

    private final AdminClient kafkaAdminClient;
    private final int topicPartitions;
    private final short topicReplicationFactor;


    @Override
    public CompletableFuture<Void> createTopicIfNotExists(String topicName) {
        ListTopicsResult listTopicsResult = kafkaAdminClient.listTopics();
        CompletableFuture<Void> topicCreationFuture = new CompletableFuture<>();

        listTopicsResult.names().whenComplete((topicNames, exception) -> {
            if (exception == null) {
                if (!topicNames.contains(topicName)) {
                    NewTopic newTopic = new NewTopic(topicName, topicPartitions, topicReplicationFactor);
                    kafkaAdminClient.createTopics(Collections.singletonList(newTopic)).all().whenComplete((result, createException) -> {
                        if (createException == null) {
                            topicCreationFuture.complete(null);
                        } else {
                            topicCreationFuture.completeExceptionally(createException);
                        }
                    });
                } else {
                    topicCreationFuture.complete(null);
                }
            } else {
                topicCreationFuture.completeExceptionally(exception);
            }
        });

        return topicCreationFuture;
    }
}
