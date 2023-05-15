package io.edpn.backend.messageprocessor.commodityv3.domain.repository;

import io.edpn.backend.messageprocessor.commodityv3.application.dto.persistence.StationEntity;
import io.edpn.backend.messageprocessor.domain.exception.DatabaseEntityNotFoundException;

import java.util.Optional;
import java.util.UUID;

public interface StationRepository {
    Optional<StationEntity> findByMarketId(long marketId);

    StationEntity findOrCreateBySystemIdAndStationName(UUID systemId, String stationName) throws DatabaseEntityNotFoundException;

    StationEntity update(StationEntity entity) throws DatabaseEntityNotFoundException;

    StationEntity create(StationEntity entity) throws DatabaseEntityNotFoundException;

    Optional<StationEntity> findById(UUID id);
}
