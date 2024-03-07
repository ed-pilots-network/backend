package io.edpn.backend.trade.adapter.kafka.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.trade.adapter.kafka.dto.KafkaMessageDto;
import io.edpn.backend.trade.adapter.kafka.dto.mapper.KafkaMessageMapper;
import io.edpn.backend.trade.application.domain.Message;
import io.edpn.backend.trade.application.port.outgoing.kafka.CreateTopicPort;
import io.edpn.backend.trade.application.port.outgoing.kafka.SendKafkaMessagePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MessageSenderTest {

    @Mock
    private CreateTopicPort createTopicPort;

    @Mock
    private KafkaMessageMapper messageMapper;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private KafkaTemplate<String, JsonNode> jsonNodekafkaTemplate;

    private SendKafkaMessagePort underTest;

    @BeforeEach
    void setUp() {
        underTest = new KafkaMessageSender(createTopicPort, messageMapper, objectMapper, jsonNodekafkaTemplate);
    }

    @Test
    void send_shouldInvokeCreateTopicPortAndSendKafkaMessage() throws JsonProcessingException {
        Message message = new Message("test-topic", "test-message");
        KafkaMessageDto kafkaMessageDto = new KafkaMessageDto("test-topic", "test-message");
        when(messageMapper.map(message)).thenReturn(kafkaMessageDto);
        JsonNode jsonNode = Mockito.mock(JsonNode.class);

        when(createTopicPort.createTopicIfNotExists(any(String.class))).thenReturn(CompletableFuture.completedFuture(null));
        when(objectMapper.readTree(any(String.class))).thenReturn(jsonNode);

        Boolean result = underTest.send(message);

        verify(createTopicPort, times(1)).createTopicIfNotExists(any(String.class));
        verify(jsonNodekafkaTemplate, times(1)).send(any(String.class), any(JsonNode.class));
        assertThat(result, equalTo(true));
    }

    @Test
    void send_shouldReturnFalse_whenJsonProcessingExceptionOccurs() throws JsonProcessingException {
        Message message = new Message("test-topic", "test-message");
        KafkaMessageDto kafkaMessageDto = new KafkaMessageDto("test-topic", "test-message");
        when(messageMapper.map(message)).thenReturn(kafkaMessageDto);

        when(createTopicPort.createTopicIfNotExists(any(String.class))).thenReturn(CompletableFuture.completedFuture(null));
        when(objectMapper.readTree(any(String.class))).thenThrow(JsonProcessingException.class);

        Boolean result = underTest.send(message);

        assertThat(result, equalTo(false));
    }

    @Test
    void send_shouldReturnFalse_whenExceptionOccursWhileCreatingTopic() {
        Message message = new Message("test-topic", "test-message");
        KafkaMessageDto kafkaMessageDto = new KafkaMessageDto("test-topic", "test-message");
        when(messageMapper.map(message)).thenReturn(kafkaMessageDto);

        when(createTopicPort.createTopicIfNotExists(any(String.class))).thenReturn(CompletableFuture.failedFuture(new ExecutionException("Exception occurred", new Throwable())));

        Boolean result = underTest.send(message);

        assertThat(result, equalTo(false));
    }
}
