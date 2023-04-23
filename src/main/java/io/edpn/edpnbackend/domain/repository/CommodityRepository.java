package io.edpn.edpnbackend.domain.repository;

import io.edpn.edpnbackend.application.dto.persistence.CommodityEntity;

import java.util.Optional;
import java.util.UUID;

public interface CommodityRepository {
    CommodityEntity findOrCreateByName(String name);

    CommodityEntity update(CommodityEntity entity);

    CommodityEntity create(CommodityEntity entity);

    Optional<CommodityEntity> findById(UUID id);
}
