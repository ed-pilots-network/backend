package io.edpn.backend.trade.application.port.outgoing.stationlandingpadsizerequest;

public interface ExistsStationLandingPadSizeRequestPort {

    boolean exists(String systemName, String stationName);
}
