package io.edpn.backend.trade.application.port.outgoing.stationarrivaldistancerequest;

public interface DeleteStationArrivalDistanceRequestPort {

    void delete(String systemName, String stationName);
}
