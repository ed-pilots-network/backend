package io.edpn.backend.exploration.application.port.outgoing;

import io.edpn.backend.exploration.application.domain.SystemCoordinateRequest;

import java.util.List;

public interface LoadAllSystemCoordinateRequestPort {

    List<SystemCoordinateRequest> loadAll();
}
