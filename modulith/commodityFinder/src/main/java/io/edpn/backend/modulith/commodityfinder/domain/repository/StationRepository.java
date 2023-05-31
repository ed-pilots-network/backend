package io.edpn.backend.modulith.commodityfinder.domain.repository;

import io.edpn.backend.modulith.commodityfinder.application.dto.persistence.StationEntity;
import io.edpn.backend.modulith.util.exception.DatabaseEntityNotFoundException;

import java.util.Optional;
import java.util.UUID;

public interface StationRepository {

    StationEntity findOrCreateBySystemIdAndStationName(UUID systemId, String stationName) throws DatabaseEntityNotFoundException;

    StationEntity update(StationEntity entity) throws DatabaseEntityNotFoundException;

    StationEntity create(StationEntity entity) throws DatabaseEntityNotFoundException;

    Optional<StationEntity> findById(UUID id);
}
