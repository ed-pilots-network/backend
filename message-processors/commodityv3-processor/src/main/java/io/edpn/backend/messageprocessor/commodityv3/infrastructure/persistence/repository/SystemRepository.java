package io.edpn.backend.messageprocessor.commodityv3.infrastructure.persistence.repository;

import io.edpn.backend.messageprocessor.commodityv3.application.dto.persistence.SystemEntity;
import io.edpn.backend.messageprocessor.commodityv3.infrastructure.persistence.mappers.SystemEntityMapper;
import io.edpn.backend.messageprocessor.domain.exception.DatabaseEntityNotFoundException;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class SystemRepository implements io.edpn.backend.messageprocessor.commodityv3.domain.repository.SystemRepository {

    private final SystemEntityMapper systemEntityMapper;

    @Override
    public SystemEntity findOrCreateByName(String name) throws DatabaseEntityNotFoundException {
        return systemEntityMapper.findByName(name)
                .orElseGet(() -> {
                    SystemEntity s = SystemEntity.builder()
                            .name(name)
                            .build();

                    return create(s);
                });
    }

    @Override
    public SystemEntity create(SystemEntity entity) throws DatabaseEntityNotFoundException {
        entity.setId(UUID.randomUUID());
        systemEntityMapper.insert(entity);
        return findById(entity.getId())
                .orElseThrow(() -> new DatabaseEntityNotFoundException("system with id: " + entity.getId() + " could not be found after create"));
    }

    @Override
    public Optional<SystemEntity> findById(UUID id) {
        return systemEntityMapper.findById(id);
    }
}
