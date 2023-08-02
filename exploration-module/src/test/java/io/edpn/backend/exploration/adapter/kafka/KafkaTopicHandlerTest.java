package io.edpn.backend.exploration.adapter.kafka;

import io.edpn.backend.exploration.application.port.outgoing.CreateTopicPort;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.KafkaFuture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class KafkaTopicHandlerTest {

    @Mock
    private AdminClient kafkaAdminClient;

    private CreateTopicPort underTest;


    @BeforeEach
    void setUp() {
        underTest = new KafkaTopicHandler(kafkaAdminClient, 1, (short) 1);
    }

    @Test
    void createTopicIfNotExists_shouldCreateNewTopicWhenTopicNameDoesNotExist() {
        // Given
        String topicName = "test-topic";
        ListTopicsResult listTopicsResult = mock(ListTopicsResult.class);
        CreateTopicsResult createTopicsResult = mock(CreateTopicsResult.class);
        KafkaFuture<Set<String>> completableFuture = KafkaFuture.completedFuture(new HashSet<>());
        when(listTopicsResult.names()).thenReturn(completableFuture);
        when(kafkaAdminClient.listTopics()).thenReturn(listTopicsResult);
        when(kafkaAdminClient.createTopics(anyList())).thenReturn(createTopicsResult);
        when(createTopicsResult.all()).thenReturn(KafkaFuture.completedFuture(null));

        // When
        underTest.createTopicIfNotExists(topicName);

        // Then
        verify(kafkaAdminClient, times(1)).createTopics(Collections.singletonList(new NewTopic(topicName, 1, (short) 1)));
    }

    @Test
    void createTopicIfNotExists_shouldNotCreateNewTopicWhenTopicNameExists() {
        // Given
        String topicName = "test-topic";
        Set<String> topicNames = new HashSet<>();
        topicNames.add(topicName);
        ListTopicsResult listTopicsResult = mock(ListTopicsResult.class);
        KafkaFuture<Set<String>> completableFuture = KafkaFuture.completedFuture(topicNames);
        when(listTopicsResult.names()).thenReturn(completableFuture);
        when(kafkaAdminClient.listTopics()).thenReturn(listTopicsResult);

        // When
        underTest.createTopicIfNotExists(topicName);

        // Then
        verify(kafkaAdminClient, never()).createTopics(anyList());
    }
}
