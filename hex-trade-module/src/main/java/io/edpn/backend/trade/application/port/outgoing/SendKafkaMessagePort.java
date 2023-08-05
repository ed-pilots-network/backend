package io.edpn.backend.trade.application.port.outgoing;

import io.edpn.backend.trade.application.dto.MessageDto;

public interface SendKafkaMessagePort {
    Boolean send(MessageDto messageDto);
}