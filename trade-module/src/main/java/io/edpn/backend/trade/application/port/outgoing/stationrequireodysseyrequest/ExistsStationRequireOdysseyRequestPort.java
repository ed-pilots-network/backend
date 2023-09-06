package io.edpn.backend.trade.application.port.outgoing.stationrequireodysseyrequest;

public interface ExistsStationRequireOdysseyRequestPort {

    boolean exists(String systemName, String stationName);
}
