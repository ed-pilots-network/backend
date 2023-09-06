package io.edpn.backend.trade.application.port.outgoing.stationarrivaldistancerequest;

public interface ExistsStationArrivalDistanceRequestPort {

    boolean exists(String systemName, String stationName);
}
