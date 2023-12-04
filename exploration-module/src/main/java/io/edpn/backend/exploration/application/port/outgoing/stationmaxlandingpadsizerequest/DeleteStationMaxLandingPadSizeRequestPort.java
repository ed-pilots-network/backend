package io.edpn.backend.exploration.application.port.outgoing.stationmaxlandingpadsizerequest;

import io.edpn.backend.util.Module;

public interface DeleteStationMaxLandingPadSizeRequestPort {

    void delete(String systemName, String stationName, Module requestingModule);
}
