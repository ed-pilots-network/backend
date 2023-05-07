package io.edpn.backend.rest.application.usecase;

import io.edpn.backend.rest.domain.model.station.Station;
import java.util.Optional;
import java.util.UUID;

public interface GetStationUsecase {

    Optional<Station> findById(UUID id);
}
