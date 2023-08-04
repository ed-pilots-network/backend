package io.edpn.backend.exploration.adapter.kafka.dto.mapper;

import io.edpn.backend.exploration.adapter.kafka.dto.KafkaMessageDto;
import io.edpn.backend.exploration.application.domain.KafkaMessage;
import io.edpn.backend.exploration.application.dto.MessageDto;

public class KafkaMessageMapper implements io.edpn.backend.exploration.application.dto.mapper.KafkaMessageMapper {
    @Override
    public MessageDto map(KafkaMessage kafkaMessage) {
        return new KafkaMessageDto(kafkaMessage.topic(), kafkaMessage.message());
    }
}
