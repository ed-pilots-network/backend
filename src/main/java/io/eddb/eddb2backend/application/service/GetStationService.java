package io.eddb.eddb2backend.application.service;

import io.eddb.eddb2backend.application.usecase.GetStationUsecase;
import io.eddb.eddb2backend.domain.model.station.Station;
import io.eddb.eddb2backend.domain.repository.StationRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetStationService implements GetStationUsecase {
    private final StationRepository stationRepository;

    @Override
    public Optional<Station> findById(UUID id) {
        return Optional.empty(); //TODO
    }
}
