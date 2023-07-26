package io.edpn.backend.exploration.infrastructure.persistence.mappers.entity;

import io.edpn.backend.exploration.domain.model.RequestDataMessage;
import io.edpn.backend.exploration.infrastructure.persistence.entity.RequestDataMessageEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(MockitoExtension.class)
public class RequestDataMessageMapperTest {


    private RequestDataMessageMapper underTest;

    @BeforeEach
    void setUp() {
        underTest = new RequestDataMessageMapper();
    }

    @Test
    void shouldMapRequestDataMessageToEntity() {
        String topic = "Test.Topic.Name";
        String message = "This is a test message";

        RequestDataMessage requestDataMessage = RequestDataMessage.builder()
                .topic(topic)
                .message(message)
                .build();

        RequestDataMessageEntity result = underTest.map(requestDataMessage);

        assertThat(result.getTopic(), is(topic));
        assertThat(result.getMessage(), is(message));
    }

    @Test
    void shouldMapEntityToRequest() {
        String topic = "Test.Topic.Name";
        String message = "This is a test message";

        RequestDataMessageEntity requestDataMessageEntity = RequestDataMessageEntity.builder()
                .topic(topic)
                .message(message)
                .build();

        RequestDataMessage result = underTest.map(requestDataMessageEntity);

        assertThat(result.getTopic(), is(topic));
        assertThat(result.getMessage(), is(message));
    }

    @Test
    void shouldSanitizeTopicName() {
        String topic = "Test@Topic*Name";
        String message = "This is a test message";

        RequestDataMessage requestDataMessage = RequestDataMessage.builder()
                .topic(topic)
                .message(message)
                .build();

        RequestDataMessageEntity result = underTest.map(requestDataMessage);

        assertThat(result.getTopic(), is("Test_Topic_Name"));
        assertThat(result.getMessage(), is(message));
    }
}
