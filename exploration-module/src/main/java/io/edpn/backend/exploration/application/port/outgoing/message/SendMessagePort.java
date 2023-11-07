package io.edpn.backend.exploration.application.port.outgoing.message;

import io.edpn.backend.exploration.application.dto.web.object.MessageDto;

public interface SendMessagePort {
    Boolean send(MessageDto messageDto);
}
