package io.edpn.backend.messageprocessor.commodityv3.domain.repository;

import io.edpn.backend.messageprocessor.commodityv3.application.dto.persistence.CommodityEntity;
import io.edpn.backend.messageprocessor.domain.exception.DatabaseEntityNotFoundException;

import java.util.Optional;
import java.util.UUID;

public interface CommodityRepository {
    CommodityEntity findOrCreateByName(String name) throws DatabaseEntityNotFoundException;

    CommodityEntity create(CommodityEntity entity) throws DatabaseEntityNotFoundException;

    Optional<CommodityEntity> findById(UUID id);
}
