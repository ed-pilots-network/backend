package io.edpn.backend.exploration.application.port.outgoing;

import io.edpn.backend.exploration.application.dto.KafkaMessageDto;

public interface SendKafkaMessagePort {
    Boolean send(KafkaMessageDto kafkaMessageDto);
}
