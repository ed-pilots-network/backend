package io.edpn.backend.trade.application.port.outgoing.station;

import io.edpn.backend.trade.application.domain.Station;
import io.edpn.backend.trade.application.domain.filter.FindStationFilter;

import java.util.List;

public interface LoadStationsByFilterPort {


    List<Station> loadByFilter(FindStationFilter findStationFilter);
}
