package io.eddb.eddb2backend.application.usecase;

import io.eddb.eddb2backend.domain.model.station.Station;

import java.util.Optional;
import java.util.UUID;

public interface GetStationUsecase {

    Optional<Station> findById(UUID id);
}
