package io.edpn.backend.exploration.application.port.outgoing.systemeliteidrequest;

public interface SystemEliteIdResponseSender {

    void sendResponsesForSystem(String systemName);
}