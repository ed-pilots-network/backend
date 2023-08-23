package io.edpn.backend.trade.adapter.web.dto.object.mapper;

import io.edpn.backend.trade.adapter.web.dto.object.RestSystemDto;
import io.edpn.backend.trade.application.domain.System;
import io.edpn.backend.trade.application.dto.web.object.SystemDto;
import io.edpn.backend.trade.application.dto.web.object.mapper.SystemDtoMapper;

public class RestSystemDtoMapper implements SystemDtoMapper {
    @Override
    public SystemDto map(System system) {
        return new RestSystemDto(
                system.getEliteId(),
                system.getName(),
                system.getXCoordinate(),
                system.getYCoordinate(),
                system.getZCoordinate()
        );
    }
}
