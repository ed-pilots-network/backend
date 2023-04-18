package io.eddb.eddb2backend.domain.repository;

import io.eddb.eddb2backend.application.dto.persistence.StationEntity;
import io.eddb.eddb2backend.domain.model.station.Station;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface StationRepository {
    UUID save(StationEntity entity);

    Optional<StationEntity> findById(UUID id);
    
    Collection<StationEntity> findByNameStartsWith(String name);

    Optional<StationEntity> findByName(String name);
}
