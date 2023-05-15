package io.edpn.backend.rest.application.service.system;

import io.edpn.backend.rest.application.usecase.system.GetSystemUseCase;
import io.edpn.backend.rest.domain.model.system.System;
import io.edpn.backend.rest.domain.repository.system.SystemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetSystemService implements GetSystemUseCase {
    
    private final SystemRepository systemRepository;
    
    @Override
    public Optional<System> findById(UUID id) {
        return systemRepository.findById(id);
    }
}
