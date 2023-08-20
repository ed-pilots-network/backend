package io.edpn.backend.trade.application.port.outgoing.system;

import io.edpn.backend.trade.application.domain.System;

public interface LoadOrCreateSystemByNamePort {
    
    System loadOrCreateSystemByName(String systemName);
}
