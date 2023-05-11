package io.edpn.backend.rest.domain.repository.common;

import io.edpn.backend.rest.domain.model.common.Allegiance;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AllegianceRepository {
    List<Allegiance> findAll();
    
    List<Allegiance> findByNameContains(String nameSubString);
    
    Allegiance update(Allegiance entity);
    
    Allegiance create(Allegiance entity);
    
    Optional<Allegiance> findById(UUID id);
}
