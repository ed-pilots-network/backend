package io.edpn.edpnbackend.domain.repository;

import io.edpn.edpnbackend.application.dto.persistence.StationEntity;

import java.util.Optional;
import java.util.UUID;

public interface StationRepository {
    StationEntity findOrCreateByMarketId(long marketId);

    StationEntity update(StationEntity entity);

    StationEntity create(StationEntity entity);

    Optional<StationEntity> findById(UUID id);
}
