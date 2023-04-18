package io.eddb.eddb2backend.domain.repository;

import io.eddb.eddb2backend.application.dto.persistence.SystemEntity;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface SystemRepository {
    UUID save(SystemEntity entity);
    
    Optional<SystemEntity> findById(UUID id);
    
    Optional<SystemEntity> findByName(String name);

    Collection<SystemEntity> findByNameStartsWith(String name);
}
