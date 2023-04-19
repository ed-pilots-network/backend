package io.eddb.eddb2backend.domain.repository;

import io.eddb.eddb2backend.application.dto.persistence.SystemEntity;

import java.util.Optional;

public interface SystemRepository {
    SystemEntity findOrCreateByName(String name);

    SystemEntity update(SystemEntity entity);

    SystemEntity create(SystemEntity entity);

    Optional<SystemEntity> findById(SystemEntity.Id id);
}
