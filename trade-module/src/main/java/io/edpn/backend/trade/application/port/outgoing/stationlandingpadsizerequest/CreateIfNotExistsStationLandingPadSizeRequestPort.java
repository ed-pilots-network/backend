package io.edpn.backend.trade.application.port.outgoing.stationlandingpadsizerequest;

public interface CreateIfNotExistsStationLandingPadSizeRequestPort {

    void createIfNotExists(String systemName, String stationName);
}
