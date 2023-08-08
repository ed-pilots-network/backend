package io.edpn.backend.exploration.application.port.outgoing.systemeliteidrequest;

import io.edpn.backend.exploration.application.domain.SystemEliteIdRequest;

import java.util.List;

public interface LoadSystemEliteIdRequestBySystemNamePort {

    List<SystemEliteIdRequest> loadByName(String systemName);
}
