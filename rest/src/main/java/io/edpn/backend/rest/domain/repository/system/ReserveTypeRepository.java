package io.edpn.backend.rest.domain.repository.system;

import io.edpn.backend.rest.domain.model.system.ReserveType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReserveTypeRepository {
    List<ReserveType> findAll();
    
    List<ReserveType> findByNameContains(String nameSubString);
    
    Optional<ReserveType> findById(UUID id);
}
