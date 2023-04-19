package io.eddb.eddb2backend.domain.repository;

import io.eddb.eddb2backend.application.dto.persistence.CommodityEntity;
import io.eddb.eddb2backend.application.dto.persistence.HistoricStationCommodityEntity;

import java.util.Optional;

public interface HistoricStationCommodityRepository {
    HistoricStationCommodityEntity update(HistoricStationCommodityEntity entity);

    Optional<HistoricStationCommodityEntity> getById(HistoricStationCommodityEntity entity);

    HistoricStationCommodityEntity create(HistoricStationCommodityEntity entity);

}
