package io.edpn.backend.exploration.application.port.outgoing.systemcoordinaterequest;

public interface SystemCoordinatesResponseSender {

    void sendResponsesForSystem(String systemName);
}