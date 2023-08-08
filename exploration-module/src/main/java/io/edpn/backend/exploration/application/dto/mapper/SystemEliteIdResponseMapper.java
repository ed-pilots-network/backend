package io.edpn.backend.exploration.application.dto.mapper;

import io.edpn.backend.exploration.application.domain.System;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemEliteIdResponse;

public interface SystemEliteIdResponseMapper {
    SystemEliteIdResponse map(System system);
}
