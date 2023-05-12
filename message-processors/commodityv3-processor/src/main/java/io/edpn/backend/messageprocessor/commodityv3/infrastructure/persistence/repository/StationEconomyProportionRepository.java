package io.edpn.backend.messageprocessor.commodityv3.infrastructure.persistence.repository;

import io.edpn.backend.messageprocessor.commodityv3.application.dto.persistence.StationEconomyProportionEntity;
import io.edpn.backend.messageprocessor.commodityv3.infrastructure.persistence.mappers.StationEconomyProportionEntityMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;


@RequiredArgsConstructor
public class StationEconomyProportionRepository implements io.edpn.backend.messageprocessor.commodityv3.domain.repository.StationEconomyProportionRepository {

    private final StationEconomyProportionEntityMapper stationEconomyProportionEntityMapper;

    @Override
    public void deleteByStationId(UUID stationId) {
        stationEconomyProportionEntityMapper.delete(stationId);
    }

    @Override
    public void insert(List<StationEconomyProportionEntity> stationEconomyProportionEntities) {
        stationEconomyProportionEntities.forEach(this::insert);
    }

    private void insert(StationEconomyProportionEntity stationEconomyProportionEntities) {
        stationEconomyProportionEntityMapper.insert(stationEconomyProportionEntities);
    }
}
