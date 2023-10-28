package io.edpn.backend.trade.application.port.outgoing.station;

import io.edpn.backend.trade.application.domain.Station;

public interface CreateOrLoadStationPort {

    Station createOrLoad(Station station);
}
