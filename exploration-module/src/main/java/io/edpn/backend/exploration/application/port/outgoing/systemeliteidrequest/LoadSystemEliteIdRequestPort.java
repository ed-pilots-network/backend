package io.edpn.backend.exploration.application.port.outgoing.systemeliteidrequest;

import io.edpn.backend.exploration.application.domain.SystemEliteIdRequest;

import java.util.Optional;

public interface LoadSystemEliteIdRequestPort {

    Optional<SystemEliteIdRequest> load(SystemEliteIdRequest systemEliteIdRequest);
}
