package io.edpn.backend.rest.domain.repository.system;

import io.edpn.backend.rest.domain.model.system.ReserveType;

import java.util.Optional;
import java.util.UUID;

public interface ReserveTypeRepository {
    ReserveType findOrCreateByName(String name);
    
    ReserveType update(ReserveType entity);
    
    ReserveType create(ReserveType entity);
    
    Optional<ReserveType> findById(UUID id);
}
