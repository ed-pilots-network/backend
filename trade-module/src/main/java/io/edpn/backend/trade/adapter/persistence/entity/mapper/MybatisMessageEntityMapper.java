package io.edpn.backend.trade.adapter.persistence.entity.mapper;

import io.edpn.backend.trade.adapter.persistence.entity.MybatisMessageEntity;
import io.edpn.backend.trade.application.domain.Message;

public class MybatisMessageEntityMapper {
    public Message map(MybatisMessageEntity mybatisMessageEntity) {
        return new Message(mybatisMessageEntity.getTopic(), mybatisMessageEntity.getMessage());
    }

    public MybatisMessageEntity map(Message message) {
        String sanitizedTopicName = this.sanitizeTopicName(message.topic());
        return MybatisMessageEntity.builder()
                .message(message.message())
                .topic(sanitizedTopicName)
                .build();
    }

    private String sanitizeTopicName(String topicName) {
        return topicName.replaceAll("[^A-Za-z0-9._\\-]", "_");
    }
}
