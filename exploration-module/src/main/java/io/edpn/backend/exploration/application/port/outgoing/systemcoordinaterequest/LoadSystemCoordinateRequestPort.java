package io.edpn.backend.exploration.application.port.outgoing.systemcoordinaterequest;

import io.edpn.backend.exploration.application.domain.SystemCoordinateRequest;

import java.util.Optional;

public interface LoadSystemCoordinateRequestPort {

    Optional<SystemCoordinateRequest> load(SystemCoordinateRequest systemCoordinateRequest);
}
