package io.edpn.backend.exploration.application.port.outgoing.systemcoordinaterequest;

import io.edpn.backend.exploration.application.domain.SystemCoordinateRequest;

import java.util.List;

public interface LoadSystemCoordinateRequestBySystemNamePort {

    List<SystemCoordinateRequest> loadByName(String systemName);
}
