package io.edpn.backend.exploration.application.port.outgoing.station;

import io.edpn.backend.exploration.application.domain.Station;

public interface SaveOrUpdateStationPort {

    Station saveOrUpdate(Station station);
}
