package io.edpn.backend.exploration.adapter.kafka.dto.mapper;

import io.edpn.backend.exploration.adapter.kafka.dto.KafkaMessageDto;
import io.edpn.backend.exploration.application.domain.Message;

public class KafkaMessageMapper {
    public KafkaMessageDto map(Message message) {
        return new KafkaMessageDto(message.topic(), message.message());
    }
}
