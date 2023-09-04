package io.edpn.backend.exploration.application.port.outgoing.systemeliteidrequest;

import io.edpn.backend.exploration.application.domain.SystemEliteIdRequest;

public interface CreateIfNotExistsSystemEliteIdRequestPort {

    void createIfNotExists(SystemEliteIdRequest systemEliteIdRequest);
}
