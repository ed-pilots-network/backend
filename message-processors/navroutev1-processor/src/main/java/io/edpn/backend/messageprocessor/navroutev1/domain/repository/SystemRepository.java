package io.edpn.backend.messageprocessor.navroutev1.domain.repository;


import io.edpn.backend.messageprocessor.navroutev1.application.dto.persistence.SystemEntity;

import java.util.Optional;
import java.util.UUID;

public interface SystemRepository {
    SystemEntity findOrCreateByName(String name);

    SystemEntity update(SystemEntity entity);

    SystemEntity create(SystemEntity entity);

    Optional<SystemEntity> findById(UUID id);
}
