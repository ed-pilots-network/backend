package io.edpn.backend.messageprocessor.commodityv3.infrastructure.persistence.repository;

import io.edpn.backend.messageprocessor.commodityv3.application.dto.persistence.StationProhibitedCommodityEntity;
import io.edpn.backend.messageprocessor.commodityv3.infrastructure.persistence.mappers.StationProhibitedCommodityEntityMapper;
import io.edpn.backend.messageprocessor.domain.exception.DatabaseEntityNotFoundException;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.UUID;

@RequiredArgsConstructor
public class StationProhibitedCommodityRepository implements io.edpn.backend.messageprocessor.commodityv3.domain.repository.StationProhibitedCommodityRepository {

    private final StationProhibitedCommodityEntityMapper stationProhibitedCommodityEntityMapper;

    @Override
    public Collection<StationProhibitedCommodityEntity> insert(UUID stationId, Collection<UUID> commodityIds) throws DatabaseEntityNotFoundException {
        stationProhibitedCommodityEntityMapper.insert(commodityIds.stream()
                .map(commodityId -> new StationProhibitedCommodityEntity(stationId, commodityId))
                .toList());
        return stationProhibitedCommodityEntityMapper.findByStationIds(stationId);
    }

    @Override
    public void deleteByStationId(UUID stationId) {
        stationProhibitedCommodityEntityMapper.delete(stationId);
    }
}
