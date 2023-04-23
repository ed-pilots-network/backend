package io.eddb.eddb2backend.domain.repository;

import io.eddb.eddb2backend.application.dto.persistence.HistoricStationCommodityMarketDatumEntity;

import java.util.Optional;

public interface HistoricStationCommodityMarketDatumRepository {
    HistoricStationCommodityMarketDatumEntity update(HistoricStationCommodityMarketDatumEntity entity);

    Optional<HistoricStationCommodityMarketDatumEntity> getById(HistoricStationCommodityMarketDatumEntity entity);

    HistoricStationCommodityMarketDatumEntity create(HistoricStationCommodityMarketDatumEntity entity);

}
