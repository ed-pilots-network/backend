package io.eddb.eddb2backend.domain.repository;

import io.eddb.eddb2backend.application.dto.persistence.StationSystemEntity;

import java.util.Optional;
import java.util.UUID;

public interface StationSystemRepository {
    StationSystemEntity update(StationSystemEntity entity);

    StationSystemEntity create(StationSystemEntity entity);

    Optional<StationSystemEntity> findById(UUID id);

    void deleteById(UUID id);
}
