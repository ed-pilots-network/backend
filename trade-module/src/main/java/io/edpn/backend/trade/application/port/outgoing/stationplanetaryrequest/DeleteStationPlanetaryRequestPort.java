package io.edpn.backend.trade.application.port.outgoing.stationplanetaryrequest;

public interface DeleteStationPlanetaryRequestPort {

    void delete(String systemName, String stationName);
}
