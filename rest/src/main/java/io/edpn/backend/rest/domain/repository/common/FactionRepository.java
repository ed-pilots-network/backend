package io.edpn.backend.rest.domain.repository.common;

import io.edpn.backend.rest.domain.model.common.Faction;

import java.util.Optional;
import java.util.UUID;

public interface FactionRepository {
    Faction findOrCreateByName(String name);
    
    Faction update(Faction entity);
    
    Faction create(Faction entity);
    
    Optional<Faction> findById(UUID id);
}
