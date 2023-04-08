package io.eddb.eddb2backend.application.usecase;

import io.eddb.eddb2backend.domain.model.station.Station;

import java.util.Optional;

public interface GetStationUsecase {

    Optional<Station> getById(Long id);
}
