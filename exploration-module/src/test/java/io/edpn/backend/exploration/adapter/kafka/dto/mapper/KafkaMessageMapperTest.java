package io.edpn.backend.exploration.adapter.kafka.dto.mapper;

import io.edpn.backend.exploration.application.domain.KafkaMessage;
import io.edpn.backend.exploration.application.dto.KafkaMessageDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(MockitoExtension.class)
class KafkaMessageMapperTest {

    private io.edpn.backend.exploration.application.dto.mapper.KafkaMessageMapper underTest;

    @BeforeEach
    void setUp() {
        underTest = new KafkaMessageMapper();
    }

    @Test
    void map_shouldReturnCorrectKafkaMessageDto() {
        // Given
        String topic = "test-topic";
        String message = "test-message";
        KafkaMessage kafkaMessage = new KafkaMessage(topic, message);

        // When
        KafkaMessageDto result = underTest.map(kafkaMessage);

        // Then
        assertThat(result.topic(), is(topic));
        assertThat(result.message(), is(message));
    }
}