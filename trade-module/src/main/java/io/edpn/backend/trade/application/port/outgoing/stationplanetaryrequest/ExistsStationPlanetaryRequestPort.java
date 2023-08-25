package io.edpn.backend.trade.application.port.outgoing.stationplanetaryrequest;

public interface ExistsStationPlanetaryRequestPort {

    boolean exists(String systemName, String stationName);
}
