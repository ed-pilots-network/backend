package io.edpn.backend.rest.domain.repository.system;

import io.edpn.backend.rest.domain.model.system.PowerState;

import java.util.Optional;
import java.util.UUID;

public interface PowerStateRepository {
    PowerState findOrCreateByName(String name);
    
    PowerState update(PowerState entity);
    
    PowerState create(PowerState entity);
    
    Optional<PowerState> findById(UUID id);
}
