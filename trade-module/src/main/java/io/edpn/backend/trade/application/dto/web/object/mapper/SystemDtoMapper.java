package io.edpn.backend.trade.application.dto.web.object.mapper;

import io.edpn.backend.trade.application.domain.System;
import io.edpn.backend.trade.application.dto.web.object.SystemDto;

public interface SystemDtoMapper {
    
    SystemDto map(System system);
}
