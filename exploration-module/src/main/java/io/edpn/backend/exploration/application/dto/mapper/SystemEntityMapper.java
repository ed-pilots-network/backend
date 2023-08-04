package io.edpn.backend.exploration.application.dto.mapper;

import io.edpn.backend.exploration.application.domain.System;
import io.edpn.backend.exploration.application.dto.SystemEntity;

public interface SystemEntityMapper {
    System map(SystemEntity systemEntity);

    SystemEntity map(System system);
}
