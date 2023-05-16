package io.edpn.backend.rest.application.usecase.system;

import io.edpn.backend.rest.domain.model.system.System;

import java.util.Optional;
import java.util.UUID;

public interface FindSystemUseCase {
    
    Optional<System> findById(UUID id);
}
