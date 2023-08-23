package io.edpn.backend.trade.application.port.outgoing.system;

import io.edpn.backend.trade.application.domain.System;

import java.util.Optional;
import java.util.UUID;

public interface LoadSystemByIdPort {
    
    Optional<System> loadById(UUID uuid);
}
