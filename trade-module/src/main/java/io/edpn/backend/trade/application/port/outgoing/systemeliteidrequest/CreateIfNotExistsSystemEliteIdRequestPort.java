package io.edpn.backend.trade.application.port.outgoing.systemeliteidrequest;

public interface CreateIfNotExistsSystemEliteIdRequestPort {

    void createIfNotExists(String systemName);
}
