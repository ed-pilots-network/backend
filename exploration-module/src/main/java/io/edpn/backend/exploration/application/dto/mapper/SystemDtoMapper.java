package io.edpn.backend.exploration.application.dto.mapper;

import io.edpn.backend.exploration.application.domain.System;
import io.edpn.backend.exploration.application.dto.SystemDto;

public interface SystemDtoMapper {
    SystemDto map(System system);
}
