package io.edpn.backend.exploration.application.port.outgoing.stationmaxlandingpadsizerequest;

import io.edpn.backend.exploration.application.domain.StationMaxLandingPadSizeRequest;

public interface CreateIfNotExistsStationMaxLandingPadSizeRequestPort {

    void createIfNotExists(StationMaxLandingPadSizeRequest stationMaxLandingPadSizeRequest);
}
