package io.edpn.backend.trade.application.port.outgoing.station;

import io.edpn.backend.trade.application.domain.Station;

public interface CreateStationPort {
    
    Station create(Station station);
}
