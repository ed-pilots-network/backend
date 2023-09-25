package io.edpn.backend.trade.application.port.outgoing.stationplanetaryrequest;

import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationDataRequest;

import java.util.List;

public interface LoadAllStationPlanetaryRequestsPort {

    List<StationDataRequest> loadAll();

}
