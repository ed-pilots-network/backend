package io.edpn.backend.commodityfinder.domain.repository;

import io.edpn.backend.commodityfinder.domain.model.Commodity;
import io.edpn.backend.commodityfinder.infrastructure.persistence.dto.CommodityEntity;
import io.edpn.backend.util.exception.DatabaseEntityNotFoundException;

import java.util.Optional;
import java.util.UUID;

public interface CommodityRepository {

    Commodity findOrCreateByName(String name) throws DatabaseEntityNotFoundException;

    Commodity create(Commodity entity) throws DatabaseEntityNotFoundException;

    Optional<Commodity> findById(UUID id);
}
