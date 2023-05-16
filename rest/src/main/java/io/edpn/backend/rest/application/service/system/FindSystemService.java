package io.edpn.backend.rest.application.service.system;

import io.edpn.backend.rest.application.usecase.system.FindSystemUseCase;
import io.edpn.backend.rest.domain.model.system.System;
import io.edpn.backend.rest.domain.repository.system.SystemRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class FindSystemService implements FindSystemUseCase {
    
    private final SystemRepository systemRepository;
    
    @Override
    public Optional<System> findById(UUID id) {
        return systemRepository.findById(id);
    }
}
