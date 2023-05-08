package io.edpn.backend.rest.domain.repository.system;

import java.util.Optional;
import java.util.UUID;

public interface SystemRepository {
    System findOrCreateByName(String name);
    
    System update(System entity);
    
    System create(System entity);
    
    Optional<System> findById(UUID id);
}
