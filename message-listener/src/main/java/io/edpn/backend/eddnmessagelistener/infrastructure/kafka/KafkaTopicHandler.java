package io.edpn.backend.eddnmessagelistener.infrastructure.kafka;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;

@RequiredArgsConstructor
public class KafkaTopicHandler {

    private final AdminClient kafkaAdminClient;
    private final int topicPartitions;
    private final short topicReplicationFactor;


    public CompletableFuture<String> createTopicIfNotExists(String topicName) {
        ListTopicsResult listTopicsResult = kafkaAdminClient.listTopics();
        CompletableFuture<String> topicCreationFuture = new CompletableFuture<>();

        listTopicsResult.names().whenComplete((topicNames, exception) -> {
            if (exception == null) {
                if (!topicNames.contains(topicName)) {
                    NewTopic newTopic = new NewTopic(topicName, topicPartitions, topicReplicationFactor);
                    kafkaAdminClient.createTopics(Collections.singletonList(newTopic)).all().whenComplete((result, createException) -> {
                        if (createException == null) {
                            topicCreationFuture.complete(topicName);
                        } else {
                            topicCreationFuture.completeExceptionally(createException);
                        }
                    });
                } else {
                    topicCreationFuture.complete(topicName);
                }
            } else {
                topicCreationFuture.completeExceptionally(exception);
            }
        });

        return topicCreationFuture;
    }
}
