package io.edpn.backend.rest.domain.repository.system;

import io.edpn.backend.rest.domain.model.system.Power;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PowerRepository {
    List<Power> findAll();
    
    List<Power> findByNameContains(String nameSubString);
    
    Optional<Power> findById(UUID id);
}
