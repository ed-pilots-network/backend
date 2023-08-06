package io.edpn.backend.exploration.application.port.outgoing;

import io.edpn.backend.exploration.application.dto.MessageDto;

public interface SendKafkaMessagePort {
    Boolean send(MessageDto messageDto);
}
