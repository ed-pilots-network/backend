package io.edpn.backend.exploration.adapter.kafka.dto.mapper;

import io.edpn.backend.exploration.adapter.kafka.dto.KafkaMessageDto;
import io.edpn.backend.exploration.application.domain.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(MockitoExtension.class)
class KafkaMessageMapperTest {

    private KafkaMessageMapper underTest;

    @BeforeEach
    void setUp() {
        underTest = new KafkaMessageMapper();
    }

    @Test
    void map_shouldReturnCorrectKafkaMessageDto() {

        String topic = "test-topic";
        String message = "test-message";
        Message kafkaMessage = new Message(topic, message);


        KafkaMessageDto result = underTest.map(kafkaMessage);


        assertThat(result.topic(), is(topic));
        assertThat(result.message(), is(message));
    }
}