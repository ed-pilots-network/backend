package io.edpn.edpnbackend.commoditymessageprocessor.domain.repository;

import io.edpn.edpnbackend.commoditymessageprocessor.application.dto.persistence.StationStationTypeEntity;

import java.util.Optional;
import java.util.UUID;

public interface StationStationTypeRepository {
    StationStationTypeEntity update(StationStationTypeEntity entity);

    StationStationTypeEntity create(StationStationTypeEntity entity);

    Optional<StationStationTypeEntity> findById(UUID stationId);

    void deleteById(UUID stationIdd);
}
