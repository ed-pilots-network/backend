package io.edpn.backend.trade.application.port.outgoing.systemeliteidrequest;

import io.edpn.backend.trade.application.domain.intermodulecommunication.SystemDataRequest;

import java.util.List;

public interface LoadAllSystemEliteIdRequestsPort {

    List<SystemDataRequest> loadAll();

}
