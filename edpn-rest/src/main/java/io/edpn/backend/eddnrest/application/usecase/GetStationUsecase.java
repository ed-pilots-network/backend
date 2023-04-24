package io.edpn.backend.eddnrest.application.usecase;

import io.edpn.backend.eddnrest.domain.model.station.Station;
import java.util.Optional;
import java.util.UUID;

public interface GetStationUsecase {

    Optional<Station> findById(UUID id);
}
