package io.edpn.edpnbackend.commoditymessageprocessor.domain.repository;

import io.edpn.edpnbackend.commoditymessageprocessor.application.dto.persistence.HistoricStationCommodityMarketDatumEntity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface HistoricStationCommodityMarketDatumRepository {
    HistoricStationCommodityMarketDatumEntity update(HistoricStationCommodityMarketDatumEntity entity);

    Optional<HistoricStationCommodityMarketDatumEntity> getById(HistoricStationCommodityMarketDatumEntity entity);

    HistoricStationCommodityMarketDatumEntity create(HistoricStationCommodityMarketDatumEntity entity);

     void cleanupRedundantData(HistoricStationCommodityMarketDatumEntity entity);

     Collection<HistoricStationCommodityMarketDatumEntity> findByCommodityIdAndTimestampBetween(UUID commodityId, LocalDateTime start, LocalDateTime end);

}
