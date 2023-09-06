package io.edpn.backend.trade.application.port.outgoing.stationarrivaldistancerequest;

public interface CreateStationArrivalDistanceRequestPort {

    void create(String systemName, String stationName);
}
