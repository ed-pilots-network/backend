package io.edpn.backend.exploration.application.dto.mapper;

import io.edpn.backend.exploration.application.domain.Message;
import io.edpn.backend.exploration.application.dto.MessageDto;

public interface MessageMapper {
    MessageDto map(Message message);
}
