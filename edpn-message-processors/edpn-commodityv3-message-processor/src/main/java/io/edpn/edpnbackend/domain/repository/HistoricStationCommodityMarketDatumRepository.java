package io.edpn.edpnbackend.domain.repository;

import io.edpn.edpnbackend.application.dto.persistence.HistoricStationCommodityMarketDatumEntity;

import java.util.Optional;

public interface HistoricStationCommodityMarketDatumRepository {
    HistoricStationCommodityMarketDatumEntity update(HistoricStationCommodityMarketDatumEntity entity);

    Optional<HistoricStationCommodityMarketDatumEntity> getById(HistoricStationCommodityMarketDatumEntity entity);

    HistoricStationCommodityMarketDatumEntity create(HistoricStationCommodityMarketDatumEntity entity);

    public void cleanupRedundantData(HistoricStationCommodityMarketDatumEntity entity);

}
