package io.edpn.backend.trade.application.port.outgoing.station;

import io.edpn.backend.trade.application.domain.Station;

import java.util.UUID;

public interface LoadStationByIdPort {
    
    Station loadById(UUID uuid);
}
