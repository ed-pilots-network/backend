package io.edpn.backend.trade.application.port.outgoing.kafka;

import io.edpn.backend.trade.application.domain.Message;

public interface SendKafkaMessagePort {
    Boolean send(Message message);
}