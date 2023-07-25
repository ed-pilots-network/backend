package io.edpn.backend.exploration.domain.repository;

import io.edpn.backend.exploration.domain.model.System;
import io.edpn.backend.util.exception.DatabaseEntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SystemRepository {

    System findOrCreateByName(String name) throws DatabaseEntityNotFoundException;

    System update(System entity);

    System create(System entity) throws DatabaseEntityNotFoundException;

    Optional<System> findById(UUID id);

    Optional<System> findByName(String name);
    List<System> findFromSearchbar(String name, int amount);
}
