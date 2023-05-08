package io.edpn.backend.rest.domain.repository.common;

import io.edpn.backend.rest.domain.model.common.Economy;

import java.util.Optional;
import java.util.UUID;

public interface EconomyRepository {
    Economy findOrCreateByName(String name);
    
    Economy update(Economy entity);
    
    Economy create(Economy entity);
    
    Optional<Economy> findById(UUID id);
}
