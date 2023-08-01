package io.edpn.backend.exploration.application.port.outgoing;

import io.edpn.backend.exploration.application.domain.KafkaMessage;

public interface SendKafkaMessagePort {
    Boolean send(KafkaMessage kafkaMessage);
}
