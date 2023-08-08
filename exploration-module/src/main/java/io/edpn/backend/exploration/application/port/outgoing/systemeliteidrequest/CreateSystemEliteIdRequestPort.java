package io.edpn.backend.exploration.application.port.outgoing.systemeliteidrequest;

import io.edpn.backend.exploration.application.domain.SystemEliteIdRequest;

public interface CreateSystemEliteIdRequestPort {

    void create(SystemEliteIdRequest systemEliteIdRequest);
}
