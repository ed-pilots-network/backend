package io.edpn.backend.trade.application.port.outgoing.stationlandingpadsizerequest;

import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationDataRequest;

import java.util.List;

public interface LoadAllStationLandingPadSizeRequestsPort {

    List<StationDataRequest> loadAll();

}
