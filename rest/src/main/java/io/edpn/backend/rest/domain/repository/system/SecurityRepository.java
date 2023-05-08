package io.edpn.backend.rest.domain.repository.system;

import io.edpn.backend.rest.domain.model.system.Security;

import java.util.Optional;
import java.util.UUID;

public interface SecurityRepository {
    Security findOrCreateByName(String name);
    
    Security update(Security entity);
    
    Security create(Security entity);
    
    Optional<Security> findById(UUID id);
}
