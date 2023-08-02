package io.edpn.backend.exploration.application.port.outgoing;

import io.edpn.backend.exploration.application.domain.SystemCoordinateRequest;

import java.util.List;

public interface LoadSystemCoordinateRequestBySystemNamePort {

    List<SystemCoordinateRequest> load(String systemName);
}
