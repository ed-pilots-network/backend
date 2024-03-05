package io.edpn.backend.trade.adapter.persistence.entity.mapper;

import io.edpn.backend.trade.adapter.persistence.entity.MessageEntity;
import io.edpn.backend.trade.application.domain.Message;

public class MessageEntityMapper {
    public Message map(MessageEntity messageEntity) {
        return new Message(messageEntity.getTopic(), messageEntity.getMessage());
    }

    public MessageEntity map(Message message) {
        String sanitizedTopicName = this.sanitizeTopicName(message.topic());
        return MessageEntity.builder()
                .message(message.message())
                .topic(sanitizedTopicName)
                .build();
    }

    private String sanitizeTopicName(String topicName) {
        return topicName.replaceAll("[^A-Za-z0-9._\\-]", "_");
    }
}
