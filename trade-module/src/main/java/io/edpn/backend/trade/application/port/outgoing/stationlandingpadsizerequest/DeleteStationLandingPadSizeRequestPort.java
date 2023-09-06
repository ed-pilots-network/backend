package io.edpn.backend.trade.application.port.outgoing.stationlandingpadsizerequest;

public interface DeleteStationLandingPadSizeRequestPort {

    void delete(String systemName, String stationName);
}
