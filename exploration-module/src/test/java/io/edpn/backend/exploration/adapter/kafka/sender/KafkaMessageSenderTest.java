package io.edpn.backend.exploration.adapter.kafka.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.edpn.backend.exploration.adapter.kafka.dto.KafkaMessageDto;
import io.edpn.backend.exploration.application.dto.MessageDto;
import io.edpn.backend.exploration.application.port.outgoing.CreateTopicPort;
import io.edpn.backend.exploration.application.port.outgoing.SendKafkaMessagePort;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class KafkaMessageSenderTest {

    @Mock
    private CreateTopicPort createTopicPort;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private KafkaTemplate<String, JsonNode> jsonNodekafkaTemplate;

    private SendKafkaMessagePort underTest;

    @BeforeEach
    void setUp() {
        underTest = new KafkaMessageSender(createTopicPort, objectMapper, jsonNodekafkaTemplate);
    }

    @Test
    void send_shouldInvokeCreateTopicPortAndSendKafkaMessage() throws JsonProcessingException {

        MessageDto messageDto = new KafkaMessageDto("test-topic", "test-message");
        JsonNode jsonNode = Mockito.mock(JsonNode.class);

        when(createTopicPort.createTopicIfNotExists(any(String.class))).thenReturn(CompletableFuture.completedFuture(null));
        when(objectMapper.readTree(any(String.class))).thenReturn(jsonNode);


        Boolean result = underTest.send(messageDto);


        verify(createTopicPort, times(1)).createTopicIfNotExists(any(String.class));
        verify(jsonNodekafkaTemplate, times(1)).send(any(String.class), any(JsonNode.class));
        assertThat(result, equalTo(true));
    }

    @Test
    void send_shouldReturnFalse_whenJsonProcessingExceptionOccurs() throws JsonProcessingException {

        MessageDto messageDto = new KafkaMessageDto("test-topic", "test-message");

        when(createTopicPort.createTopicIfNotExists(any(String.class))).thenReturn(CompletableFuture.completedFuture(null));
        when(objectMapper.readTree(any(String.class))).thenThrow(JsonProcessingException.class);


        Boolean result = underTest.send(messageDto);


        assertThat(result, equalTo(false));
    }

    @Test
    void send_shouldReturnFalse_whenExceptionOccursWhileCreatingTopic() {

        MessageDto messageDto = new KafkaMessageDto("test-topic", "test-message");

        when(createTopicPort.createTopicIfNotExists(any(String.class))).thenReturn(CompletableFuture.failedFuture(new ExecutionException("Exception occurred", new Throwable())));


        Boolean result = underTest.send(messageDto);


        assertThat(result, equalTo(false));
    }
}
