package io.edpn.backend.exploration.application.port.outgoing;

import io.edpn.backend.util.Module;

public interface DeleteSystemCoordinateRequestPort {

    void delete(String systemName, Module requestingModule);
}
