package io.edpn.backend.messageprocessor.commodityv3.domain.repository;

import io.edpn.backend.messageprocessor.commodityv3.application.dto.persistence.StationStationTypeEntity;

import java.util.Optional;
import java.util.UUID;

public interface StationStationTypeRepository {
    StationStationTypeEntity update(StationStationTypeEntity entity);

    StationStationTypeEntity create(StationStationTypeEntity entity);

    Optional<StationStationTypeEntity> findById(UUID stationId);

    void deleteById(UUID stationIdd);
}
