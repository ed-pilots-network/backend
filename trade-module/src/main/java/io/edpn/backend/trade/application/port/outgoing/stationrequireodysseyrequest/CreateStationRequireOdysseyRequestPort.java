package io.edpn.backend.trade.application.port.outgoing.stationrequireodysseyrequest;

public interface CreateStationRequireOdysseyRequestPort {

    void create(String systemName, String stationName);
}
