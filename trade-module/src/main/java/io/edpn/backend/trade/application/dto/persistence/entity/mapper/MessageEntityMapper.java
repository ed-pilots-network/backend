package io.edpn.backend.trade.application.dto.persistence.entity.mapper;

import io.edpn.backend.trade.application.domain.Message;
import io.edpn.backend.trade.application.dto.persistence.entity.MessageEntity;

public interface MessageEntityMapper<T extends MessageEntity> {
    
    Message map(MessageEntity messageEntity);
    
    T map(Message message);
}
