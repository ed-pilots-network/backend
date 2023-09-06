package io.edpn.backend.trade.application.port.outgoing.stationplanetaryrequest;

public interface CreateStationPlanetaryRequestPort {

    void create(String systemName, String stationName);
}
