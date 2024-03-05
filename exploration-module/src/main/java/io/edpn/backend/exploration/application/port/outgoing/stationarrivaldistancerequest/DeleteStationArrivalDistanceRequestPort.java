package io.edpn.backend.exploration.application.port.outgoing.stationarrivaldistancerequest;

import io.edpn.backend.util.Module;

public interface DeleteStationArrivalDistanceRequestPort {

    void delete(String systemName, String stationName, Module requestingModule);
}
