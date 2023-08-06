package io.edpn.backend.exploration.application.port.outgoing;

import io.edpn.backend.exploration.application.domain.SystemCoordinateRequest;

public interface CreateSystemCoordinateRequestPort {

    void create(SystemCoordinateRequest systemCoordinateRequest);
}
