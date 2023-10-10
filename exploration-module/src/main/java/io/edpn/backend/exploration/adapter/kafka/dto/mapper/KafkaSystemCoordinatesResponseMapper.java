package io.edpn.backend.exploration.adapter.kafka.dto.mapper;

import io.edpn.backend.exploration.application.domain.System;
import io.edpn.backend.exploration.application.dto.persistence.entity.mapper.SystemCoordinatesResponseMapper;
import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemCoordinatesResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class KafkaSystemCoordinatesResponseMapper implements SystemCoordinatesResponseMapper {

    @Override
    public SystemCoordinatesResponse map(System system) {

        return new SystemCoordinatesResponse(
                system.name(),
                system.coordinate().x(),
                system.coordinate().y(),
                system.coordinate().z());
    }
}
