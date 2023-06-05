package io.edpn.backend.commodityfinder.infrastructure.persistence.repository;

import io.edpn.backend.commodityfinder.application.dto.persistence.SystemEntity;
import io.edpn.backend.commodityfinder.domain.repository.SystemRepository;
import io.edpn.backend.commodityfinder.infrastructure.persistence.mappers.SystemEntityMapper;
import io.edpn.backend.util.IdGenerator;
import io.edpn.backend.util.exception.DatabaseEntityNotFoundException;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class MybatisSystemRepository implements SystemRepository {

    private final IdGenerator idGenerator;
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
                .orElseThrow(() -> new DatabaseEntityNotFoundException("system with id: " + entity.getId() + " could not be found after update"));
    }

    @Override
    public SystemEntity create(SystemEntity entity) {
        if (Objects.isNull(entity.getId())) {
            entity.setId(idGenerator.generateId());
        }
        systemEntityMapper.insert(entity);
        return findById(entity.getId())
                .orElseThrow(() -> new DatabaseEntityNotFoundException("system with id: " + entity.getId() + " could not be found after create"));
    }

    @Override
    public Optional<SystemEntity> findById(UUID id) {
        return systemEntityMapper.findById(id);
    }
}
