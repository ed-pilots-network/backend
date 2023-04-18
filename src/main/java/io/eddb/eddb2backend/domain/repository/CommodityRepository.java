package io.eddb.eddb2backend.domain.repository;

import io.eddb.eddb2backend.application.dto.persistence.CommodityEntity;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface CommodityRepository {
    UUID save(CommodityEntity commodityEntity);

    Optional<CommodityEntity> findById(UUID id);

    Collection<CommodityEntity> findByNameStartsWith(String name);
}
