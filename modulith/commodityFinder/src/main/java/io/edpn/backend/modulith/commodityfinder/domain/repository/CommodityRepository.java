package io.edpn.backend.modulith.commodityfinder.domain.repository;

import io.edpn.backend.modulith.commodityfinder.application.dto.persistence.CommodityEntity;
import io.edpn.backend.modulith.util.exception.DatabaseEntityNotFoundException;

import java.util.Optional;
import java.util.UUID;

public interface CommodityRepository {

    CommodityEntity findOrCreateByName(String name) throws DatabaseEntityNotFoundException;

    CommodityEntity create(CommodityEntity entity) throws DatabaseEntityNotFoundException;

    Optional<CommodityEntity> findById(UUID id);
}
