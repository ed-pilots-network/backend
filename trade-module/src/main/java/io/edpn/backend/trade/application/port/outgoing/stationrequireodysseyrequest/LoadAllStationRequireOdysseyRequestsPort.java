package io.edpn.backend.trade.application.port.outgoing.stationrequireodysseyrequest;

import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationDataRequest;

import java.util.List;

public interface LoadAllStationRequireOdysseyRequestsPort {

    List<StationDataRequest> loadAll();

}
