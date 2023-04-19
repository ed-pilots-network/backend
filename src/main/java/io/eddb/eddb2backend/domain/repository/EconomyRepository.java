package io.eddb.eddb2backend.domain.repository;

import io.eddb.eddb2backend.application.dto.persistence.EconomyEntity;

import java.util.Optional;

public interface EconomyRepository {
    EconomyEntity findOrCreateByName(String name);

    EconomyEntity update(EconomyEntity entity);

    EconomyEntity create(EconomyEntity entity);

    Optional<EconomyEntity> findById(EconomyEntity.Id id);
}
