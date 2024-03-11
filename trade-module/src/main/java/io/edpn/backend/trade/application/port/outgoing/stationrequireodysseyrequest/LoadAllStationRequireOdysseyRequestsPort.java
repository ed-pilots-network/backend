package io.edpn.backend.trade.application.port.outgoing.stationrequireodysseyrequest;

import io.edpn.backend.trade.application.domain.intermodulecommunication.StationDataRequest;

import java.util.List;

public interface LoadAllStationRequireOdysseyRequestsPort {

    List<StationDataRequest> loadAll();

}
