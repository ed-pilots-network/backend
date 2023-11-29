package io.edpn.backend.exploration.application.port.outgoing.station;

import io.edpn.backend.exploration.application.domain.Station;
import java.util.Optional;

public interface LoadStationPort {

    Optional<Station> load(String systemName, String name);
}
