package io.edpn.backend.messageprocessor.commodityv3.domain.repository;

import io.edpn.backend.messageprocessor.commodityv3.application.dto.persistence.StationProhibitedCommodityEntity;
import io.edpn.backend.messageprocessor.domain.exception.DatabaseEntityNotFoundException;

import java.util.Collection;
import java.util.UUID;

public interface StationProhibitedCommodityRepository {
    Collection<StationProhibitedCommodityEntity> insert(UUID stationId, Collection<UUID> commodityIds) throws DatabaseEntityNotFoundException;

    void deleteByStationId(UUID stationId);
}
