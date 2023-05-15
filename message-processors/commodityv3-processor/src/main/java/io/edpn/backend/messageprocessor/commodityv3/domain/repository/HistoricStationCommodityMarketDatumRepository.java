package io.edpn.backend.messageprocessor.commodityv3.domain.repository;

import io.edpn.backend.messageprocessor.commodityv3.application.dto.persistence.HistoricStationCommodityMarketDatumEntity;
import io.edpn.backend.messageprocessor.domain.exception.DatabaseEntityNotFoundException;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface HistoricStationCommodityMarketDatumRepository {
    Optional<HistoricStationCommodityMarketDatumEntity> getByStationIdAndCommodityIdAndTimestamp(UUID stationId, UUID commodityId, LocalDateTime timestamp);

    Optional<HistoricStationCommodityMarketDatumEntity> getById(UUID historicStationCommodityMarketDatumId);

    HistoricStationCommodityMarketDatumEntity create(HistoricStationCommodityMarketDatumEntity entity) throws DatabaseEntityNotFoundException;

    void cleanupRedundantData(UUID stationId, UUID commodityId);

    Collection<HistoricStationCommodityMarketDatumEntity> findByCommodityIdAndTimestampBetween(UUID commodityId, LocalDateTime start, LocalDateTime end);

}
