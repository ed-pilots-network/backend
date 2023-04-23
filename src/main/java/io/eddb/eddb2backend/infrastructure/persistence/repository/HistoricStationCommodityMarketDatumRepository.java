package io.eddb.eddb2backend.infrastructure.persistence.repository;

import io.eddb.eddb2backend.application.dto.persistence.HistoricStationCommodityMarketDatumEntity;
import io.eddb.eddb2backend.infrastructure.persistence.mappers.HistoricStationCommodityMarketDatumEntityMapper;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class HistoricStationCommodityMarketDatumRepository implements io.eddb.eddb2backend.domain.repository.HistoricStationCommodityMarketDatumRepository {

    private final HistoricStationCommodityMarketDatumEntityMapper historicStationCommodityMarketDatumEntityMapper;

    @Override
    public HistoricStationCommodityMarketDatumEntity update(HistoricStationCommodityMarketDatumEntity entity) {
        historicStationCommodityMarketDatumEntityMapper.update(entity);

        return getById(entity)
                .orElseThrow(() -> new RuntimeException("historicStationCommodity with id: " + getIdString(entity) + " could not be found after update"));
    }

    @Override
    public Optional<HistoricStationCommodityMarketDatumEntity> getById(HistoricStationCommodityMarketDatumEntity entity) {
        return historicStationCommodityMarketDatumEntityMapper.findById(entity.getStationId(), entity.getCommodityId(), entity.getTimestamp());
    }

    @Override
    public HistoricStationCommodityMarketDatumEntity create(HistoricStationCommodityMarketDatumEntity entity) {
        historicStationCommodityMarketDatumEntityMapper.insert(entity);

        return getById(entity)
                .orElseThrow(() -> new RuntimeException("historicStationCommodity with id: " + getIdString(entity) + " could not be found after create"));
    }

    private String getIdString(HistoricStationCommodityMarketDatumEntity entity) {
        return String.format("[stationId: '%s', commodityId: '%s', timestamp: '%s']", entity.getStationId(), entity.getCommodityId(), entity.getTimestamp());
    }
}
