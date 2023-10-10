package io.edpn.backend.exploration.application.dto.persistence.entity.mapper;

import io.edpn.backend.exploration.application.domain.SystemEliteIdRequest;
import io.edpn.backend.exploration.application.dto.persistence.entity.SystemEliteIdRequestEntity;

public interface SystemEliteIdRequestEntityMapper<T extends SystemEliteIdRequestEntity> {
    SystemEliteIdRequest map(SystemEliteIdRequestEntity systemEliteIdRequestEntity);

    T map(SystemEliteIdRequest systemEliteIdRequest);
}
