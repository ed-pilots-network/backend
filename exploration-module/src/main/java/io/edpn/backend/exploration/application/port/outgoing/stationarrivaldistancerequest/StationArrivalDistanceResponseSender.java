package io.edpn.backend.exploration.application.port.outgoing.stationarrivaldistancerequest;

public interface StationArrivalDistanceResponseSender {

    void sendResponsesForStation(String systemName, String stationName);
}
