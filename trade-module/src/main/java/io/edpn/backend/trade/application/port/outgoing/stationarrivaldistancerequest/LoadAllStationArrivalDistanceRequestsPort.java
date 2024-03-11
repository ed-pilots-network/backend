package io.edpn.backend.trade.application.port.outgoing.stationarrivaldistancerequest;

import io.edpn.backend.trade.application.domain.intermodulecommunication.StationDataRequest;

import java.util.List;

public interface LoadAllStationArrivalDistanceRequestsPort {

    List<StationDataRequest> loadAll();

}
