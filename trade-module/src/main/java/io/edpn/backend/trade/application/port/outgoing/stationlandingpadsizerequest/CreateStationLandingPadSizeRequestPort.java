package io.edpn.backend.trade.application.port.outgoing.stationlandingpadsizerequest;

public interface CreateStationLandingPadSizeRequestPort {

    void create(String systemName, String stationName);
}
