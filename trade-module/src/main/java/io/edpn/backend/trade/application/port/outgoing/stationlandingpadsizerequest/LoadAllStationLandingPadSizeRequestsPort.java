package io.edpn.backend.trade.application.port.outgoing.stationlandingpadsizerequest;

import io.edpn.backend.trade.application.domain.intermodulecommunication.StationDataRequest;

import java.util.List;

public interface LoadAllStationLandingPadSizeRequestsPort {

    List<StationDataRequest> loadAll();

}
