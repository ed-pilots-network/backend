package io.eddb.eddb2backend.infrastructure.persistence.repository;

import io.eddb.eddb2backend.application.dto.persistence.EconomyEntity;
import io.eddb.eddb2backend.infrastructure.persistence.mappers.EconomyEntityMapper;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class EconomyRepository implements io.eddb.eddb2backend.domain.repository.EconomyRepository {

    private final EconomyEntityMapper economyEntityMapper;

    @Override
    public EconomyEntity findOrCreateByName(String name) {
        return economyEntityMapper.findByName(name)
                .orElseGet(() -> {
                    var s = EconomyEntity.builder()
                            .name(name)
                            .build();
                    return create(s);
                });
    }

    @Override
    public EconomyEntity update(EconomyEntity entity) {
        economyEntityMapper.update(entity);
        return findById(entity.getId())
                .orElseThrow(() -> new RuntimeException("economy with id: " + entity.getId() + " could not be found after update"));
    }

    @Override
    public EconomyEntity create(EconomyEntity entity) {
        entity.setId(UUID.randomUUID());
        economyEntityMapper.insert(entity);
        return findById(entity.getId())
                .orElseThrow(() -> new RuntimeException("economy with id: " + entity.getId() + " could not be found after create"));
    }

    @Override
    public Optional<EconomyEntity> findById(UUID id) {
        return economyEntityMapper.findById(id);
    }
}
