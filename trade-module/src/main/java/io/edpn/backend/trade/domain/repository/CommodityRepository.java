package io.edpn.backend.trade.domain.repository;

import io.edpn.backend.trade.domain.model.Commodity;
import io.edpn.backend.util.exception.DatabaseEntityNotFoundException;

import java.util.Optional;
import java.util.UUID;

public interface CommodityRepository {

    Commodity findOrCreateByName(String name) throws DatabaseEntityNotFoundException;

    Commodity create(Commodity commodity) throws DatabaseEntityNotFoundException;

    Optional<Commodity> findById(UUID id);
}
