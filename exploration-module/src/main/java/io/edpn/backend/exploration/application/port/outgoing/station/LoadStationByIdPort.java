package io.edpn.backend.exploration.application.port.outgoing.station;

import io.edpn.backend.exploration.application.domain.Station;
import java.util.Optional;
import java.util.UUID;

public interface LoadStationByIdPort {

    Optional<Station> load(UUID uuid);
}
