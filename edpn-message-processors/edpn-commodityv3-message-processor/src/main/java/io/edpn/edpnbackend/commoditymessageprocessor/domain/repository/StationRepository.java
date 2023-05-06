package io.edpn.edpnbackend.commoditymessageprocessor.domain.repository;

import io.edpn.edpnbackend.commoditymessageprocessor.application.dto.persistence.StationEntity;

import java.util.Optional;
import java.util.UUID;

public interface StationRepository {
    StationEntity findOrCreateByMarketId(long marketId);

    StationEntity update(StationEntity entity);

    StationEntity create(StationEntity entity);

    Optional<StationEntity> findById(UUID id);
}
