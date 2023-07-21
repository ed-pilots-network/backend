package io.edpn.backend.exploration.infrastructure.persistence.repository;

import io.edpn.backend.exploration.domain.model.System;
import io.edpn.backend.exploration.domain.repository.SystemRepository;
import io.edpn.backend.exploration.infrastructure.persistence.mappers.entity.SystemMapper;
import io.edpn.backend.exploration.infrastructure.persistence.mappers.mybatis.SystemEntityMapper;
import io.edpn.backend.util.IdGenerator;
import io.edpn.backend.util.exception.DatabaseEntityNotFoundException;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class MybatisSystemRepository implements SystemRepository {

    private final IdGenerator idGenerator;
    private final SystemMapper systemMapper;
    private final SystemEntityMapper systemEntityMapper;

    @Override
    public System findOrCreateByName(String name) {
        return systemEntityMapper.findByName(name)
                .map(systemMapper::map)
                .orElseGet(() -> {
                    System s = System.builder()
                            .name(name)
                            .build();

                    return create(s);
                });
    }

    @Override
    public System update(System system) {
        var entity = systemMapper.map(system);

        systemEntityMapper.update(entity);
        return findById(entity.getId())
                .orElseThrow(() -> new DatabaseEntityNotFoundException("system with id: " + entity.getId() + " could not be found after update"));
    }

    @Override
    public System create(System system) {
        var entity = systemMapper.map(system);

        if (Objects.isNull(entity.getId())) {
            entity.setId(idGenerator.generateId());
        }
        systemEntityMapper.insert(entity);
        return findById(entity.getId())
                .orElseThrow(() -> new DatabaseEntityNotFoundException("system with id: " + entity.getId() + " could not be found after create"));
    }

    @Override
    public Optional<System> findById(UUID id) {
        return systemEntityMapper.findById(id)
                .map(systemMapper::map);
    }

    @Override
    public Optional<System> findByName(String name) {
        return systemEntityMapper.findByName(name)
                .map(systemMapper::map);
    }
}
