package io.edpn.backend.exploration.application.port.outgoing.stationmaxlandingpadsizerequest;

import io.edpn.backend.exploration.application.domain.StationMaxLandingPadSizeRequest;
import java.util.List;

public interface LoadAllStationMaxLandingPadSizeRequestPort {

    List<StationMaxLandingPadSizeRequest> loadAll();
}
