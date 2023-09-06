package io.edpn.backend.trade.application.port.outgoing.stationrequireodysseyrequest;

public interface DeleteStationRequireOdysseyRequestPort {

    void delete(String systemName, String stationName);
}
