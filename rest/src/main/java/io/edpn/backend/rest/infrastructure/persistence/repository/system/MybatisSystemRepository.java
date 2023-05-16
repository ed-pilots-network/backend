package io.edpn.backend.rest.infrastructure.persistence.repository.system;

import io.edpn.backend.rest.domain.model.system.System;
import io.edpn.backend.rest.domain.repository.system.SystemRepository;
import io.edpn.backend.rest.infrastructure.persistence.mappers.system.SystemMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class MybatisSystemRepository implements SystemRepository {
    
    private final SystemMapper systemMapper;
    
    @Override
    public List<System> findByNameContains(String nameSubString) {
        return systemMapper.findByNameContains(nameSubString);
    }
    
    @Override
    public Optional<System> findById(UUID id) {
        return systemMapper.findById(id);
    }
}
