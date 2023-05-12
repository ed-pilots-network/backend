package io.edpn.backend.messageprocessor.commodityv3.infrastructure.persistence.repository;

import io.edpn.backend.messageprocessor.commodityv3.application.dto.persistence.HistoricStationCommodityMarketDatumEntity;
import io.edpn.backend.messageprocessor.commodityv3.infrastructure.persistence.mappers.HistoricStationCommodityMarketDatumEntityMapper;
import io.edpn.backend.messageprocessor.domain.exception.DatabaseEntityNotFoundException;
import io.edpn.backend.messageprocessor.domain.util.IdGenerator;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class HistoricStationCommodityMarketDatumRepository implements io.edpn.backend.messageprocessor.commodityv3.domain.repository.HistoricStationCommodityMarketDatumRepository {

    private final IdGenerator idGenerator;
    private final HistoricStationCommodityMarketDatumEntityMapper historicStationCommodityMarketDatumEntityMapper;


    @Override
    public HistoricStationCommodityMarketDatumEntity create(HistoricStationCommodityMarketDatumEntity entity) throws DatabaseEntityNotFoundException {
        if (Objects.isNull(entity.getId())) {
            entity.setId(idGenerator.generateId());
        }
        historicStationCommodityMarketDatumEntityMapper.insert(entity);

        return getById(entity.getId())
                .orElseThrow(() -> new RuntimeException("historicStationCommodity with id: " + entity.getId() + " could not be found after create"));
    }

    @Override
    public Optional<HistoricStationCommodityMarketDatumEntity> getByStationIdAndCommodityIdAndTimestamp(UUID stationId, UUID commodityId, LocalDateTime timestamp) {
        return historicStationCommodityMarketDatumEntityMapper.findByStationIdAndCommodityIdAndTimestamp(stationId, commodityId, timestamp);
    }

    @Override
    public Optional<HistoricStationCommodityMarketDatumEntity> getById(UUID historicStationCommodityMarketDatumId) {
        return historicStationCommodityMarketDatumEntityMapper.findById(historicStationCommodityMarketDatumId);
    }

    @Override
    public void cleanupRedundantData(UUID stationId, UUID commodityId) {
        historicStationCommodityMarketDatumEntityMapper.deleteObsoleteForStationAndCommodity(stationId, commodityId);
    }

    @Override
    public Collection<HistoricStationCommodityMarketDatumEntity> findByCommodityIdAndTimestampBetween(UUID commodityId, LocalDateTime start, LocalDateTime end) {
        return historicStationCommodityMarketDatumEntityMapper.findByCommodityIdAndTimestampBetween(commodityId, start, end);
    }
}
