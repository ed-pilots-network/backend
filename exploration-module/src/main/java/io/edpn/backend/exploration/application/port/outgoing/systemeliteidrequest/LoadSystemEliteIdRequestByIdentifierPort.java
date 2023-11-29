package io.edpn.backend.exploration.application.port.outgoing.systemeliteidrequest;

import io.edpn.backend.exploration.application.domain.SystemEliteIdRequest;

import java.util.List;

public interface LoadSystemEliteIdRequestByIdentifierPort {

    List<SystemEliteIdRequest> loadByIdentifier(String systemName);
}
