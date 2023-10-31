package io.edpn.backend.exploration.adapter.kafka.dto.mapper;

import io.edpn.backend.exploration.adapter.kafka.dto.KafkaMessageDto;
import io.edpn.backend.exploration.application.domain.Message;
import io.edpn.backend.exploration.application.dto.web.object.mapper.MessageDtoMapper;

public class KafkaMessageMapper implements MessageDtoMapper {
    @Override
    public KafkaMessageDto map(Message message) {
        return new KafkaMessageDto(message.topic(), message.message());
    }
}
