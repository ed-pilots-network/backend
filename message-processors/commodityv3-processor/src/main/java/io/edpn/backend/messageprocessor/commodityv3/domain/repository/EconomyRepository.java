package io.edpn.backend.messageprocessor.commodityv3.domain.repository;

import io.edpn.backend.messageprocessor.commodityv3.application.dto.persistence.EconomyEntity;
import io.edpn.backend.messageprocessor.domain.exception.DatabaseEntityNotFoundException;

import java.util.Optional;
import java.util.UUID;

public interface EconomyRepository {
    EconomyEntity findOrCreateByName(String name) throws DatabaseEntityNotFoundException;

    EconomyEntity create(EconomyEntity entity) throws DatabaseEntityNotFoundException;

    Optional<EconomyEntity> findById(UUID id);
}
