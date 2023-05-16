package io.edpn.backend.rest.application.usecase.station;

import io.edpn.backend.rest.domain.model.station.Station;
import java.util.Optional;
import java.util.UUID;

public interface FindStationUseCase {

    Optional<Station> findById(UUID id);
}
