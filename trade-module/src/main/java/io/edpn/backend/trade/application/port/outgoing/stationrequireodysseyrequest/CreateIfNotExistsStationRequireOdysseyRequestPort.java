package io.edpn.backend.trade.application.port.outgoing.stationrequireodysseyrequest;

public interface CreateIfNotExistsStationRequireOdysseyRequestPort {

    void createIfNotExists(String systemName, String stationName);
}
