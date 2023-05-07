package io.edpn.backend.messageprocessor.commodityv3.domain.repository;

import io.edpn.backend.messageprocessor.commodityv3.application.dto.persistence.CommodityEntity;

import java.util.Optional;
import java.util.UUID;

public interface CommodityRepository {
    CommodityEntity findOrCreateByName(String name);

    CommodityEntity update(CommodityEntity entity);

    CommodityEntity create(CommodityEntity entity);

    Optional<CommodityEntity> findById(UUID id);
}
