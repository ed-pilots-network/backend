package io.edpn.backend.exploration.application.dto.web.object.mapper;

import io.edpn.backend.exploration.application.domain.System;
import io.edpn.backend.exploration.application.dto.web.object.SystemDto;

public interface SystemDtoMapper {
    SystemDto map(System system);
}
