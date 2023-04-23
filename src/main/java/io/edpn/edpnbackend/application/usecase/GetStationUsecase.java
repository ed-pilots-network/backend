package io.edpn.edpnbackend.application.usecase;

import io.edpn.edpnbackend.domain.model.station.Station;

import java.util.Optional;
import java.util.UUID;

public interface GetStationUsecase {

    Optional<Station> findById(UUID id);
}
