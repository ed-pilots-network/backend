package io.edpn.backend.trade.application.port.outgoing.systemcoordinaterequest;

public interface ExistsSystemCoordinateRequestPort {

    boolean exists(String systemName);
}
