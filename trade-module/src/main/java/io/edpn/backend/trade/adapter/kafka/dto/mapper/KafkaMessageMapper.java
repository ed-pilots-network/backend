package io.edpn.backend.trade.adapter.kafka.dto.mapper;

import io.edpn.backend.trade.adapter.kafka.dto.KafkaMessageDto;
import io.edpn.backend.trade.application.domain.Message;

public class KafkaMessageMapper {
    public KafkaMessageDto map(Message message) {
        return new KafkaMessageDto(message.topic(), message.message());
    }
}
