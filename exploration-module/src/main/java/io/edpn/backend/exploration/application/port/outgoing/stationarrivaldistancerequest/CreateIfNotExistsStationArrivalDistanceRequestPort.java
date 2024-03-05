package io.edpn.backend.exploration.application.port.outgoing.stationarrivaldistancerequest;

import io.edpn.backend.exploration.application.domain.StationArrivalDistanceRequest;

public interface CreateIfNotExistsStationArrivalDistanceRequestPort {

    void createIfNotExists(StationArrivalDistanceRequest stationArrivalDistanceRequest);
}
