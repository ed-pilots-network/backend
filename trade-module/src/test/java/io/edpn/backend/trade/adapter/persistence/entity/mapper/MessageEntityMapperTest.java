package io.edpn.backend.trade.adapter.persistence.entity.mapper;

import io.edpn.backend.trade.adapter.persistence.entity.MessageEntity;
import io.edpn.backend.trade.application.domain.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(MockitoExtension.class)
class MessageEntityMapperTest {

    private MessageEntityMapper underTest;

    @BeforeEach
    public void setUp() {
        underTest = new MessageEntityMapper();
    }

    @Test
    public void testMap_givenEntity_shouldReturnDomainObject() {

        // Setup the Message Entity with test data
        MessageEntity entity = MessageEntity.builder()
                .topic("topic")
                .message("message")
                .build();

        // Map the entity to a Message object
        Message domainObject = underTest.map(entity);

        // Verify that the result matches the expected values
        assertThat(domainObject.topic(), is("topic"));
        assertThat(domainObject.message(), is("message"));
    }

    @Test
    public void testMap_givenDomainObject_shouldReturnEntity() {

        // Setup the Message object with test data
        Message domainObject = new Message("topic", "message");

        // Map the domainObject to a Message entity
        MessageEntity entity = underTest.map(domainObject);

        // Verify that the result matches the expected values
        assertThat(entity.getTopic(), is("topic"));
        assertThat(entity.getMessage(), is("message"));
    }

}