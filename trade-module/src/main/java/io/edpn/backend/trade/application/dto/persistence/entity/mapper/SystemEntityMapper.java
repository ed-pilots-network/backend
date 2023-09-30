package io.edpn.backend.trade.application.dto.persistence.entity.mapper;

import io.edpn.backend.trade.application.domain.System;
import io.edpn.backend.trade.application.dto.persistence.entity.SystemEntity;

public interface SystemEntityMapper<T extends SystemEntity>{
    
    System map(SystemEntity systemEntity);
    
    T map(System system);
}
