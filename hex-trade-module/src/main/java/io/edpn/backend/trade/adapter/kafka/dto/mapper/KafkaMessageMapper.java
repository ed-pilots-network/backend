package io.edpn.backend.trade.adapter.kafka.dto.mapper;

import io.edpn.backend.trade.adapter.kafka.dto.KafkaMessageDto;
import io.edpn.backend.trade.application.domain.Message;
import io.edpn.backend.trade.application.dto.MessageDto;
import io.edpn.backend.trade.application.dto.mapper.MessageMapper;

public class KafkaMessageMapper implements MessageMapper {
    @Override
    public MessageDto map(Message message) {
        return new KafkaMessageDto(message.topic(), message.message());
    }
}
