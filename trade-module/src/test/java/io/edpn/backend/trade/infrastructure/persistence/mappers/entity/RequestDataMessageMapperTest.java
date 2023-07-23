package io.edpn.backend.trade.infrastructure.persistence.mappers.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.TextNode;
import io.edpn.backend.trade.domain.model.RequestDataMessage;
import io.edpn.backend.trade.infrastructure.persistence.entity.RequestDataMessageEntity;
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
    public void setup() {
        underTest = new RequestDataMessageMapper();
    }

    @Test
    public void shouldMapRequestDataMessageToRequestDataMessageEntity() {
        String topic = "Test.Topic.Name";
        JsonNode message = new TextNode("Test Message");

        RequestDataMessage requestDataMessage = RequestDataMessage.builder()
                .topic(topic)
                .message(message)
                .build();

        RequestDataMessageEntity requestDataMessageEntity = underTest.map(requestDataMessage);

        assertThat(requestDataMessageEntity.getTopic(), is(topic));
        assertThat(requestDataMessageEntity.getMessage(), is("\"Test Message\""));
    }

    @Test
    public void shouldSanitizeTopicName() {
        String topic = "Test?Topic:Name";
        JsonNode message = new TextNode("Test Message");

        RequestDataMessage requestDataMessage = RequestDataMessage.builder()
                .topic(topic)
                .message(message)
                .build();

        RequestDataMessageEntity requestDataMessageEntity = underTest.map(requestDataMessage);

        assertThat(requestDataMessageEntity.getTopic(), is("Test_Topic_Name"));
        assertThat(requestDataMessageEntity.getMessage(), is("\"Test Message\""));
    }
}
