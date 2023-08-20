package io.edpn.backend.trade.application.port.outgoing.system;

import java.util.UUID;

public interface LoadSystemByIdPort {
    
    System loadById(UUID uuid);
}
