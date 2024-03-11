package io.edpn.backend.trade.application.port.outgoing.systemcoordinaterequest;

import io.edpn.backend.trade.application.domain.intermodulecommunication.SystemDataRequest;

import java.util.List;

public interface LoadAllSystemCoordinateRequestsPort {

    List<SystemDataRequest> loadAll();

}
