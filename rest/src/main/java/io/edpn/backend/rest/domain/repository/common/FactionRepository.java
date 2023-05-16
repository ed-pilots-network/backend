package io.edpn.backend.rest.domain.repository.common;

import io.edpn.backend.rest.domain.model.common.Faction;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FactionRepository {
    List<Faction> findAll();
    
    List<Faction> findByNameContains(String nameSubString);
    
    Optional<Faction> findById(UUID id);
}
