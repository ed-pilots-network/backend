package io.edpn.edpnbackend.commoditymessageprocessor.domain.repository;

import io.edpn.edpnbackend.commoditymessageprocessor.application.dto.persistence.EconomyEntity;

import java.util.Optional;
import java.util.UUID;

public interface EconomyRepository {
    EconomyEntity findOrCreateByName(String name);

    EconomyEntity update(EconomyEntity entity);

    EconomyEntity create(EconomyEntity entity);

    Optional<EconomyEntity> findById(UUID id);
}
