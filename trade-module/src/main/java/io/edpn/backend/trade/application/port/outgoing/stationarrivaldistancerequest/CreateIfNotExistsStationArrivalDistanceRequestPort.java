package io.edpn.backend.trade.application.port.outgoing.stationarrivaldistancerequest;

public interface CreateIfNotExistsStationArrivalDistanceRequestPort {

    void createIfNotExists(String systemName, String stationName);
}
