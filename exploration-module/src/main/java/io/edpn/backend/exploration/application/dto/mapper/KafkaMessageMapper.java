package io.edpn.backend.exploration.application.dto.mapper;

import io.edpn.backend.exploration.application.domain.KafkaMessage;
import io.edpn.backend.exploration.application.dto.KafkaMessageDto;

public interface KafkaMessageMapper {
    KafkaMessageDto map(KafkaMessage kafkaMessage);
}
