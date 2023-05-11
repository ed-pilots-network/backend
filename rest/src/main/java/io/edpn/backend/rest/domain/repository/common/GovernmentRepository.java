package io.edpn.backend.rest.domain.repository.common;

import io.edpn.backend.rest.domain.model.common.Government;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GovernmentRepository {
    List<Government> findAll();
    
    List<Government> findByNameContains(String nameSubString);
    
    Government update(Government entity);
    
    Government create(Government entity);
    
    Optional<Government> findById(UUID id);
}
