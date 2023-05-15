package io.edpn.backend.messageprocessor.commodityv3.domain.repository;

import io.edpn.backend.messageprocessor.commodityv3.application.dto.persistence.StationEconomyProportionEntity;

import java.util.List;
import java.util.UUID;

public interface StationEconomyProportionRepository {
    void deleteByStationId(UUID stationId);

    void insert(List<StationEconomyProportionEntity> stationEconomyProportionEntities);
}
