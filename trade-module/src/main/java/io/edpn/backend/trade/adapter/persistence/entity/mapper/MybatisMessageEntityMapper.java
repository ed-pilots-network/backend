package io.edpn.backend.trade.adapter.persistence.entity.mapper;

import io.edpn.backend.trade.adapter.persistence.entity.MybatisMessageEntity;
import io.edpn.backend.trade.application.domain.Message;
import io.edpn.backend.trade.application.dto.persistence.entity.MessageEntity;
import io.edpn.backend.trade.application.dto.persistence.entity.mapper.MessageEntityMapper;

public class MybatisMessageEntityMapper implements MessageEntityMapper<MybatisMessageEntity> {
    @Override
    public Message map(MessageEntity messageEntity) {
        return Message.builder()
                .message(messageEntity.getMessage())
                .topic(messageEntity.getTopic())
                .build();
    }
    
    @Override
    public MybatisMessageEntity map(Message message) {
        String sanitizedTopicName = this.sanitizeTopicName(message.getTopic());
        return MybatisMessageEntity.builder()
                .message(message.getMessage())
                .topic(sanitizedTopicName)
                .build();
    }
    
    private String sanitizeTopicName(String topicName) {
        return topicName.replaceAll("[^A-Za-z0-9._\\-]", "_");
    }
}
