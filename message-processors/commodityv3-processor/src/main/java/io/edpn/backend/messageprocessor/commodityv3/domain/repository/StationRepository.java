package io.edpn.backend.messageprocessor.commodityv3.domain.repository;

import io.edpn.backend.messageprocessor.commodityv3.application.dto.persistence.StationEntity;

import java.util.Optional;
import java.util.UUID;

public interface StationRepository {
    Optional<StationEntity> findByMarketId(long marketId);
    StationEntity findOrCreateBySystemIdAndStationName(UUID systemId, String stationName);

    StationEntity update(StationEntity entity);

    StationEntity create(StationEntity entity);

    Optional<StationEntity> findById(UUID id);
}
