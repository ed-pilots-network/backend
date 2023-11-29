package io.edpn.backend.exploration.application.port.outgoing.stationmaxlandingpadsizerequest;

public interface StationMaxLandingPadSizeResponseSender {

    void sendResponsesForStation(String systemName, String stationName);
}
