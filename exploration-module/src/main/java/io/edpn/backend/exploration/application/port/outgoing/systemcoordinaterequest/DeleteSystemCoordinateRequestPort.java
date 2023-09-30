package io.edpn.backend.exploration.application.port.outgoing.systemcoordinaterequest;

import io.edpn.backend.util.Module;

public interface DeleteSystemCoordinateRequestPort {

    void delete(String systemName, Module requestingModule);
}
