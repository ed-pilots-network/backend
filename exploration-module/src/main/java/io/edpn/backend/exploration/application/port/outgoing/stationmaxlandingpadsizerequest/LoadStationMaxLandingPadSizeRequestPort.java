package io.edpn.backend.exploration.application.port.outgoing.stationmaxlandingpadsizerequest;

import io.edpn.backend.exploration.application.domain.StationMaxLandingPadSizeRequest;
import java.util.Optional;

public interface LoadStationMaxLandingPadSizeRequestPort {

    Optional<StationMaxLandingPadSizeRequest> load(StationMaxLandingPadSizeRequest stationMaxLandingPadSizeRequest);
}
