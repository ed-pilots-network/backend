package io.edpn.backend.commodityfinder.domain.repository;

import io.edpn.backend.commodityfinder.application.dto.persistence.CommodityEntity;
import io.edpn.backend.util.exception.DatabaseEntityNotFoundException;

import java.util.Optional;
import java.util.UUID;

public interface CommodityRepository {

    CommodityEntity findOrCreateByName(String name) throws DatabaseEntityNotFoundException;

    CommodityEntity create(CommodityEntity entity) throws DatabaseEntityNotFoundException;

    Optional<CommodityEntity> findById(UUID id);
}
