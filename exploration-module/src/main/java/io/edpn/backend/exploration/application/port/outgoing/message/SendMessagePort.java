package io.edpn.backend.exploration.application.port.outgoing.message;

import io.edpn.backend.exploration.application.domain.Message;

public interface SendMessagePort {
    Boolean send(Message messageDto);
}
