package io.edpn.backend.exploration.adapter.kafka.dto.mapper;

import io.edpn.backend.exploration.application.domain.System;
import io.edpn.backend.exploration.application.dto.persistence.entity.mapper.SystemEliteIdResponseMapper;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemEliteIdResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class KafkaSystemEliteIdResponseMapper implements SystemEliteIdResponseMapper {

    @Override
    public SystemEliteIdResponse map(System system) {

        return new SystemEliteIdResponse(
                system.name(),
                system.eliteId());
    }
}
