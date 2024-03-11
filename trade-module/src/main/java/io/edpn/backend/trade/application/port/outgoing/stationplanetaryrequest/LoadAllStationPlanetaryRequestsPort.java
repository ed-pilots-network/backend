package io.edpn.backend.trade.application.port.outgoing.stationplanetaryrequest;

import io.edpn.backend.trade.application.domain.intermodulecommunication.StationDataRequest;

import java.util.List;

public interface LoadAllStationPlanetaryRequestsPort {

    List<StationDataRequest> loadAll();

}
