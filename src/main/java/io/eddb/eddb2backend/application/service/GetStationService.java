package io.eddb.eddb2backend.application.service;

import io.eddb.eddb2backend.application.usecase.GetStationUsecase;
import io.eddb.eddb2backend.domain.model.station.Station;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class GetStationService implements GetStationUsecase {

    @Override
    public Optional<Station> findById(UUID id) {
        return Optional.empty(); //TODO
    }
}
