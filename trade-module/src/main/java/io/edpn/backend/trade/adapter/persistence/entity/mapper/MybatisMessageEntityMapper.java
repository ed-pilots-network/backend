package io.edpn.backend.trade.adapter.persistence.entity.mapper;

import io.edpn.backend.trade.adapter.persistence.entity.MybatisMessageEntity;
import io.edpn.backend.trade.application.domain.Message;
import io.edpn.backend.trade.application.dto.persistence.entity.MessageEntity;
import io.edpn.backend.trade.application.dto.persistence.entity.mapper.MessageEntityMapper;

public class MybatisMessageEntityMapper implements MessageEntityMapper<MybatisMessageEntity> {
    @Override
    public Message map(MessageEntity messageEntity) {
        return new Message(messageEntity.getTopic(), messageEntity.getMessage());
    }

    @Override
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
