package io.edpn.backend.commodityfinder.domain.repository;

import io.edpn.backend.commodityfinder.application.dto.persistence.StationEntity;
import io.edpn.backend.commodityfinder.application.dto.persistence.SystemEntity;
import io.edpn.backend.util.exception.DatabaseEntityNotFoundException;

import java.util.Optional;
import java.util.UUID;

public interface StationRepository {

    StationEntity findOrCreateBySystemAndStationName(SystemEntity systemEntity, String stationName) throws DatabaseEntityNotFoundException;

    StationEntity update(StationEntity entity) throws DatabaseEntityNotFoundException;

    StationEntity create(StationEntity entity) throws DatabaseEntityNotFoundException;

    Optional<StationEntity> findById(UUID id);
}
