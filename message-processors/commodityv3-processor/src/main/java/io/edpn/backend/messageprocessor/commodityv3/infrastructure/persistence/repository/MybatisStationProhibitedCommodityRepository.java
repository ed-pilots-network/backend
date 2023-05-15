package io.edpn.backend.messageprocessor.commodityv3.infrastructure.persistence.repository;

import io.edpn.backend.messageprocessor.commodityv3.application.dto.persistence.StationProhibitedCommodityEntity;
import io.edpn.backend.messageprocessor.commodityv3.infrastructure.persistence.mappers.StationProhibitedCommodityEntityMapper;
import io.edpn.backend.messageprocessor.domain.exception.DatabaseEntityNotFoundException;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.UUID;

@RequiredArgsConstructor
public class MybatisStationProhibitedCommodityRepository implements io.edpn.backend.messageprocessor.commodityv3.domain.repository.StationProhibitedCommodityRepository {

    private final StationProhibitedCommodityEntityMapper stationProhibitedCommodityEntityMapper;

    @Override
    public Collection<StationProhibitedCommodityEntity> insert(UUID stationId, Collection<UUID> commodityIds) throws DatabaseEntityNotFoundException {
        commodityIds.forEach(id -> this.insert(stationId, id));
        return stationProhibitedCommodityEntityMapper.findByStationIds(stationId);
    }


    private void insert(UUID stationId, UUID commodityId) throws DatabaseEntityNotFoundException {
        stationProhibitedCommodityEntityMapper.insert(new StationProhibitedCommodityEntity(stationId, commodityId));
    }

    @Override
    public void deleteByStationId(UUID stationId) {
        stationProhibitedCommodityEntityMapper.delete(stationId);
    }
}
