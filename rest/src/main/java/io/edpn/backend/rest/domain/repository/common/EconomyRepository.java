package io.edpn.backend.rest.domain.repository.common;

import io.edpn.backend.rest.domain.model.common.Economy;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EconomyRepository {
    List<Economy> findAll();
    
    List<Economy> findByNameContains(String nameSubString);
    
    Optional<Economy> findById(UUID id);
}
