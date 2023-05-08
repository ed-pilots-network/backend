package io.edpn.backend.rest.domain.repository.common;

import io.edpn.backend.rest.domain.model.common.Government;

import java.util.Optional;
import java.util.UUID;

public interface GovernmentRepository {
    Government findOrCreateByName(String name);
    
    Government update(Government entity);
    
    Government create(Government entity);
    
    Optional<Government> findById(UUID id);
}
