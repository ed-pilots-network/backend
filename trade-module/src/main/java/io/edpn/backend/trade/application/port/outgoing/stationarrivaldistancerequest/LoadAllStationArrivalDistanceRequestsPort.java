package io.edpn.backend.trade.application.port.outgoing.stationarrivaldistancerequest;

import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationDataRequest;

import java.util.List;

public interface LoadAllStationArrivalDistanceRequestsPort {

    List<StationDataRequest> loadAll();

}
