package io.edpn.backend.trade.application.port.outgoing.station;

import io.edpn.backend.trade.application.domain.Station;
import io.edpn.backend.trade.application.domain.System;

public interface LoadOrCreateBySystemAndStationNamePort {
    
    Station loadOrCreateBySystemAndStationName(System system, String stationName);
}
