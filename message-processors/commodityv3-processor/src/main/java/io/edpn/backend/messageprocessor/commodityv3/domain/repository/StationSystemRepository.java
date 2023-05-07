package io.edpn.backend.messageprocessor.commodityv3.domain.repository;

import io.edpn.backend.messageprocessor.commodityv3.application.dto.persistence.StationSystemEntity;

import java.util.Optional;
import java.util.UUID;

public interface StationSystemRepository {
    StationSystemEntity update(StationSystemEntity entity);

    StationSystemEntity create(StationSystemEntity entity);

    Optional<StationSystemEntity> findById(UUID id);

    void deleteById(UUID id);
}
