package io.edpn.backend.trade.application.dto.web.object.mapper;

import io.edpn.backend.trade.application.domain.Message;
import io.edpn.backend.trade.application.dto.web.object.MessageDto;

public interface MessageMapper {
    MessageDto map(Message message);
}
