package io.edpn.edpnbackend.commoditymessageprocessor.domain.repository;

import io.edpn.edpnbackend.commoditymessageprocessor.application.dto.persistence.CommodityEntity;

import java.util.Optional;
import java.util.UUID;

public interface CommodityRepository {
    CommodityEntity findOrCreateByName(String name);

    CommodityEntity update(CommodityEntity entity);

    CommodityEntity create(CommodityEntity entity);

    Optional<CommodityEntity> findById(UUID id);
}
