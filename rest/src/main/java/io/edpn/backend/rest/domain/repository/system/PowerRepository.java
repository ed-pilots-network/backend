package io.edpn.backend.rest.domain.repository.system;

import io.edpn.backend.rest.domain.model.system.Power;

import java.util.Optional;
import java.util.UUID;

public interface PowerRepository {
    Power findOrCreateByName(String name);
    
    Power update(Power entity);
    
    Power create(Power entity);
    
    Optional<Power> findById(UUID id);
}
