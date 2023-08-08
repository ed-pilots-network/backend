package io.edpn.backend.exploration.application.dto.mapper;

import io.edpn.backend.exploration.application.domain.SystemEliteIdRequest;
import io.edpn.backend.exploration.application.dto.SystemEliteIdRequestEntity;

public interface SystemEliteIdRequestEntityMapper<T extends SystemEliteIdRequestEntity> {
    SystemEliteIdRequest map(SystemEliteIdRequestEntity systemEliteIdRequestEntity);

    T map(SystemEliteIdRequest systemEliteIdRequest);
}
