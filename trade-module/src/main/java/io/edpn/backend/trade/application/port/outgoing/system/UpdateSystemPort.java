package io.edpn.backend.trade.application.port.outgoing.system;

import io.edpn.backend.trade.application.domain.System;

public interface UpdateSystemPort {
    
    System update(System station);
}
