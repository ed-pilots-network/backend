package io.edpn.backend.exploration.application.port.outgoing.stationarrivaldistancerequest;

import io.edpn.backend.exploration.application.domain.StationArrivalDistanceRequest;

import java.util.List;

public interface LoadAllStationArrivalDistanceRequestPort {

    List<StationArrivalDistanceRequest> loadAll();
}
