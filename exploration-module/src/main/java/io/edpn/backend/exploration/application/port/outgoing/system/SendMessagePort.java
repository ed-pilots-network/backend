package io.edpn.backend.exploration.application.port.outgoing.system;

import io.edpn.backend.exploration.application.dto.MessageDto;

public interface SendMessagePort {
    Boolean send(MessageDto messageDto);
}
