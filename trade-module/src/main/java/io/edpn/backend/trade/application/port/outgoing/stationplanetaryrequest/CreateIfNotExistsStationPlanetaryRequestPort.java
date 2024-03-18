package io.edpn.backend.trade.application.port.outgoing.stationplanetaryrequest;

public interface CreateIfNotExistsStationPlanetaryRequestPort {

    void createIfNotExists(String systemName, String stationName);
}
