package io.edpn.backend.exploration.application.port.outgoing.systemcoordinaterequest;

import io.edpn.backend.exploration.application.domain.SystemCoordinateRequest;

public interface CreateIfNotExistsSystemCoordinateRequestPort {

    void createIfNotExists(SystemCoordinateRequest systemCoordinateRequest);
}
