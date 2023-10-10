package io.edpn.backend.exploration.application.dto.web.object.mapper;

import io.edpn.backend.exploration.application.domain.Message;
import io.edpn.backend.exploration.application.dto.web.object.MessageDto;

public interface MessageDtoMapper {
    MessageDto map(Message message);
}
