package io.edpn.backend.exploration.application.dto.mapper;

import io.edpn.backend.exploration.adapter.persistence.entity.SystemEntity;
import io.edpn.backend.exploration.application.domain.System;

public interface SystemEntityMapper {
    System map(SystemEntity systemEntity);

    SystemEntity map(System system);
}
