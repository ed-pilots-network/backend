package io.edpn.backend.rest.domain.repository.system;

import io.edpn.backend.rest.domain.model.system.System;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SystemRepository {
    List<System> findByNameContains(String nameSubString);
    
    Optional<System> findById(UUID id);
}
