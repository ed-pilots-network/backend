package io.edpn.backend.rest.application.service.station;

import io.edpn.backend.rest.application.usecase.station.FindStationUseCase;
import io.edpn.backend.rest.domain.model.station.Station;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class FindStationService implements FindStationUseCase {
    
    @Override
    public Optional<Station> findById(UUID id) {
        return Optional.empty(); //TODO
    }
}
