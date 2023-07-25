package io.edpn.backend.exploration.infrastructure.persistence.mappers.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import io.edpn.backend.exploration.domain.model.RequestDataMessage;
import io.edpn.backend.exploration.infrastructure.persistence.entity.RequestDataMessageEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class RequestDataMessageMapperTest {

    private RequestDataMessageMapper underTest;

    @BeforeEach
    void setUp() {
        underTest = new RequestDataMessageMapper();
    }

    @Test
    void shouldMapRequestDataMessageToEntity() {
        String topic = "Test.Topic.Name";
        JsonNode message = JsonNodeFactory.instance.textNode("This is a test message");

        RequestDataMessage requestDataMessage = RequestDataMessage.builder()
                .topic(topic)
                .message(message)
                .build();

        RequestDataMessageEntity result = underTest.map(requestDataMessage);

        assertThat(result.getTopic(), is(topic));
        assertThat(result.getMessage(), is(message.toString()));
    }

    @Test
    void shouldSanitizeTopicName() {
        String topic = "Test@Topic*Name";
        JsonNode message = JsonNodeFactory.instance.textNode("This is a test message");

        RequestDataMessage requestDataMessage = RequestDataMessage.builder()
                .topic(topic)
                .message(message)
                .build();

        RequestDataMessageEntity result = underTest.map(requestDataMessage);

        assertThat(result.getTopic(), is("Test_Topic_Name"));
        assertThat(result.getMessage(), is(message.toString()));
    }
}
