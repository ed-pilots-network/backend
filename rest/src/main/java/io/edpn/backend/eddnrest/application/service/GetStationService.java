package io.edpn.backend.eddnrest.application.service;

import io.edpn.backend.eddnrest.application.usecase.GetStationUsecase;
import io.edpn.backend.eddnrest.domain.model.station.Station;
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
