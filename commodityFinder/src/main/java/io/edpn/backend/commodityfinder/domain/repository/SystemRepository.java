package io.edpn.backend.commodityfinder.domain.repository;

import io.edpn.backend.commodityfinder.application.dto.persistence.SystemEntity;
import io.edpn.backend.util.exception.DatabaseEntityNotFoundException;

import java.util.Optional;
import java.util.UUID;

public interface SystemRepository {

    SystemEntity findOrCreateByName(String name) throws DatabaseEntityNotFoundException;

    SystemEntity update(SystemEntity entity);

    SystemEntity create(SystemEntity entity) throws DatabaseEntityNotFoundException;

    Optional<SystemEntity> findById(UUID id);
}
