package io.edpn.backend.rest.infrastructure.persistence.repository.system;

import io.edpn.backend.rest.domain.model.system.System;
import io.edpn.backend.rest.infrastructure.persistence.mappers.system.SystemMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class SystemRepository implements io.edpn.backend.rest.domain.repository.system.SystemRepository {
    
    private final SystemMapper systemMapper;
    
    @Override
    public List<System> findByNameContains(String nameSubString) {
        return systemMapper.findByNameContains(nameSubString);
    }
    
    @Override
    public System update(System entity) {
        systemMapper.update(entity);
        return findById(entity.getId())
                .orElseThrow(() -> new RuntimeException("allegiance with id: " + entity.getId() + "could not be found after update"));
    }
    
    @Override
    public System create(System entity) {
        entity.setId(UUID.randomUUID());
        systemMapper.insert(entity);
        return findById(entity.getId())
                .orElseThrow(() -> new RuntimeException("allegiance with id: " + entity.getId() + "could not be found after create"));
    }
    
    @Override
    public Optional<System> findById(UUID id) {
        return systemMapper.findById(id);
    }
}
