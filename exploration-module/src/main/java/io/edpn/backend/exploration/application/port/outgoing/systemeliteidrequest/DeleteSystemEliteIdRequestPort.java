package io.edpn.backend.exploration.application.port.outgoing.systemeliteidrequest;

import io.edpn.backend.util.Module;

public interface DeleteSystemEliteIdRequestPort {

    void delete(String systemName, Module requestingModule);
}
