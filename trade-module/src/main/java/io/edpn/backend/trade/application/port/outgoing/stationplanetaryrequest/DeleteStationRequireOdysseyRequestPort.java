package io.edpn.backend.trade.application.port.outgoing.stationplanetaryrequest;

public interface DeleteStationRequireOdysseyRequestPort {

    void delete(String systemName, String stationName);
}
