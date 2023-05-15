package io.edpn.backend.messageprocessor.commodityv3.domain.repository;

import io.edpn.backend.messageprocessor.commodityv3.application.dto.persistence.SystemEntity;
import io.edpn.backend.messageprocessor.domain.exception.DatabaseEntityNotFoundException;

import java.util.Optional;
import java.util.UUID;

public interface SystemRepository {
    SystemEntity findOrCreateByName(String name) throws DatabaseEntityNotFoundException;

    SystemEntity create(SystemEntity entity) throws DatabaseEntityNotFoundException;

    Optional<SystemEntity> findById(UUID id);
}
