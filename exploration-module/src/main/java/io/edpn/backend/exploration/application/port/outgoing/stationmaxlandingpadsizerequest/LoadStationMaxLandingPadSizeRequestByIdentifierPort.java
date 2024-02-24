package io.edpn.backend.exploration.application.port.outgoing.stationmaxlandingpadsizerequest;

import io.edpn.backend.exploration.application.domain.StationMaxLandingPadSizeRequest;
import java.util.List;

public interface LoadStationMaxLandingPadSizeRequestByIdentifierPort {

    List<StationMaxLandingPadSizeRequest> loadByIdentifier(String systemName, String stationName);
}
