package io.edpn.backend.rest.domain.repository.common;

import io.edpn.backend.rest.domain.model.common.Allegiance;
import java.util.Optional;
import java.util.UUID;

public interface AllegianceRepository {
    Allegiance findOrCreateByName(String name);
    
    Allegiance update(Allegiance entity);
    
    Allegiance create(Allegiance entity);
    
    Optional<Allegiance> findById(UUID id);
}
