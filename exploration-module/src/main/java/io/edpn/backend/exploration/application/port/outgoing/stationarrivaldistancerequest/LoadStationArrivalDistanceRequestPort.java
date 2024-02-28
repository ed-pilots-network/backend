package io.edpn.backend.exploration.application.port.outgoing.stationarrivaldistancerequest;

import io.edpn.backend.exploration.application.domain.StationArrivalDistanceRequest;
import io.edpn.backend.exploration.application.domain.StationMaxLandingPadSizeRequest;

import java.util.Optional;

public interface LoadStationArrivalDistanceRequestPort {

    Optional<StationArrivalDistanceRequest> load(StationArrivalDistanceRequest stationArrivalDistanceRequest);
}
