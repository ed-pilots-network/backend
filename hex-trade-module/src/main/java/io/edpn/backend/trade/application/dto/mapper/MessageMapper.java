package io.edpn.backend.trade.application.dto.mapper;

import io.edpn.backend.trade.application.domain.Message;
import io.edpn.backend.trade.application.dto.MessageDto;

public interface MessageMapper {
    MessageDto map(Message message);
}
