package io.edpn.backend.trade.application.port.outgoing.systemeliteidrequest;

import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemDataRequest;

import java.util.List;

public interface LoadAllSystemEliteIdRequestsPort {

    List<SystemDataRequest> loadAll();

}
