package io.eddb.eddb2backend.infrastructure.persistence.repository;

import io.eddb.eddb2backend.application.dto.persistence.SystemEntity;
import io.eddb.eddb2backend.infrastructure.persistence.mappers.SystemEntityMapper;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class SystemRepository implements io.eddb.eddb2backend.domain.repository.SystemRepository {

    private final SystemEntityMapper systemEntityMapper;

    @Override
    public SystemEntity findOrCreateByName(String name) {
        return systemEntityMapper.findByName(name)
                .orElseGet(() -> {
                    SystemEntity s = SystemEntity.builder()
                            .name(name)
                            .build();

                    return create(s);
                });
    }

    @Override
    public SystemEntity update(SystemEntity entity) {
        systemEntityMapper.update(entity);
        return findById(entity.getId())
                .orElseThrow(() -> new RuntimeException("system with id: " + entity.getId() + " could not be found after update"));
    }

    @Override
    public SystemEntity create(SystemEntity entity) {
        entity.setId(UUID.randomUUID());
        systemEntityMapper.insert(entity);
        return findById(entity.getId())
                .orElseThrow(() -> new RuntimeException("system with id: " + entity.getId() + " could not be found after create"));
    }

    @Override
    public Optional<SystemEntity> findById(UUID id) {
        return systemEntityMapper.findById(id);
    }
}
