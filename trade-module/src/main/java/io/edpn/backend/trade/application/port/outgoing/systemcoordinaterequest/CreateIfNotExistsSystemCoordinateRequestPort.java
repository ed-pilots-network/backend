package io.edpn.backend.trade.application.port.outgoing.systemcoordinaterequest;

public interface CreateIfNotExistsSystemCoordinateRequestPort {

    void createIfNotExists(String systemName);
}
