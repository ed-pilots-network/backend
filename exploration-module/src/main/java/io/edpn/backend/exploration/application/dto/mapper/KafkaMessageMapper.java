package io.edpn.backend.exploration.application.dto.mapper;

import io.edpn.backend.exploration.application.domain.KafkaMessage;
import io.edpn.backend.exploration.application.dto.MessageDto;

public interface KafkaMessageMapper {
    MessageDto map(KafkaMessage kafkaMessage);
}
