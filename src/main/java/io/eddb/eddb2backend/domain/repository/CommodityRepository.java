package io.eddb.eddb2backend.domain.repository;

import io.eddb.eddb2backend.application.dto.persistence.CommodityEntity;

import java.util.Optional;

public interface CommodityRepository {
    CommodityEntity findOrCreateByName(String name);

    CommodityEntity update(CommodityEntity entity);

    CommodityEntity create(CommodityEntity entity);

    Optional<CommodityEntity> findById(CommodityEntity.Id id);
}
