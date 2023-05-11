package io.edpn.backend.messageprocessor.commodityv3.domain.repository;

import io.edpn.backend.messageprocessor.commodityv3.application.dto.persistence.HistoricStationCommodityMarketDatumEntity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface HistoricStationCommodityMarketDatumRepository {
    HistoricStationCommodityMarketDatumEntity update(HistoricStationCommodityMarketDatumEntity entity);

    Optional<HistoricStationCommodityMarketDatumEntity> getByStationIdAndCommodityIdAndTimestamp(UUID stationId, UUID commodityId, LocalDateTime timestamp);
    Optional<HistoricStationCommodityMarketDatumEntity> getById(UUID historicStationCommodityMarketDatumId);

    HistoricStationCommodityMarketDatumEntity create(HistoricStationCommodityMarketDatumEntity entity);

     void cleanupRedundantData(UUID stationId, UUID commodityId);

     Collection<HistoricStationCommodityMarketDatumEntity> findByCommodityIdAndTimestampBetween(UUID commodityId, LocalDateTime start, LocalDateTime end);

}
