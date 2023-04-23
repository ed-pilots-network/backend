package io.edpn.edpnbackend.application.service;

import io.edpn.edpnbackend.application.usecase.GetStationUsecase;
import io.edpn.edpnbackend.domain.model.station.Station;
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
