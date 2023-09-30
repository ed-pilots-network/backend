package io.edpn.backend.trade.application.port.outgoing.systemcoordinaterequest;

import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemDataRequest;

import java.util.List;

public interface LoadAllSystemCoordinateRequestsPort {

    List<SystemDataRequest> loadAll();

}
