package io.edpn.backend.exploration.application.port.outgoing;

public interface DeleteSystemCoordinateRequestPort {

    void delete(String systemName, String requestingModule);
}
