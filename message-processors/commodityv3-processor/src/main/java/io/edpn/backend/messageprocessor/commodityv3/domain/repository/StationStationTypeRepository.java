package io.edpn.backend.messageprocessor.commodityv3.domain.repository;

import io.edpn.backend.messageprocessor.commodityv3.application.dto.persistence.StationStationTypeEntity;
import io.edpn.backend.messageprocessor.domain.exception.DatabaseEntityNotFoundException;

import java.util.Optional;
import java.util.UUID;

public interface StationStationTypeRepository {
    StationStationTypeEntity update(StationStationTypeEntity entity) throws DatabaseEntityNotFoundException;

    StationStationTypeEntity create(StationStationTypeEntity entity) throws DatabaseEntityNotFoundException;

    Optional<StationStationTypeEntity> findById(UUID stationId);

}
