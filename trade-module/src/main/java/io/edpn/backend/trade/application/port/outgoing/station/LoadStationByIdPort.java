package io.edpn.backend.trade.application.port.outgoing.station;

import io.edpn.backend.trade.application.domain.Station;

import java.util.Optional;
import java.util.UUID;

public interface LoadStationByIdPort {
    
    Optional<Station> loadById(UUID uuid);
}
