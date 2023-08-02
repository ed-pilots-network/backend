package io.edpn.backend.exploration.adapter.kafka.dto.mapper;

import io.edpn.backend.exploration.application.domain.KafkaMessage;
import io.edpn.backend.exploration.application.dto.KafkaMessageDto;

public class KafkaMessageMapper implements io.edpn.backend.exploration.application.dto.mapper.KafkaMessageMapper {
    @Override
    public KafkaMessageDto map(KafkaMessage kafkaMessage) {
        return new io.edpn.backend.exploration.adapter.kafka.dto.KafkaMessageDto(kafkaMessage.topic(), kafkaMessage.message());
    }
}
