package io.edpn.backend.trade.application.port.outgoing.stationarrivaldistancerequest;

public interface DeleteStationRequireOdysseyRequestPort {

    void delete(String systemName, String stationName);
}
