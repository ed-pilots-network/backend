package io.edpn.backend.commodityfinder.domain.repository;

import io.edpn.backend.commodityfinder.domain.model.Station;
import io.edpn.backend.commodityfinder.domain.model.System;
import io.edpn.backend.util.exception.DatabaseEntityNotFoundException;

import java.util.Optional;
import java.util.UUID;

public interface StationRepository {


    Station findOrCreateBySystemAndStationName(System systemEntity, String stationName) throws DatabaseEntityNotFoundException;

    Station update(Station entity) throws DatabaseEntityNotFoundException;

    Station create(Station entity) throws DatabaseEntityNotFoundException;

    Optional<Station> findById(UUID id);
}
