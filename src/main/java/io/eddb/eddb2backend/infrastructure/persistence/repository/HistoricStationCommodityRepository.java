package io.eddb.eddb2backend.infrastructure.persistence.repository;

import io.eddb.eddb2backend.application.dto.persistence.HistoricStationCommodityEntity;
import io.eddb.eddb2backend.infrastructure.persistence.mappers.HistoricStationCommodityEntityMapper;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class HistoricStationCommodityRepository implements io.eddb.eddb2backend.domain.repository.HistoricStationCommodityRepository {

    private final HistoricStationCommodityEntityMapper historicStationCommodityEntityMapper;

    @Override
    public HistoricStationCommodityEntity update(HistoricStationCommodityEntity entity) {
        historicStationCommodityEntityMapper.update(entity);

        return getById(entity)
                .orElseThrow(() -> new RuntimeException("historicStationCommodity with id: " + getIdString(entity) + " could not be found after update"));
    }

    @Override
    public Optional<HistoricStationCommodityEntity> getById(HistoricStationCommodityEntity entity) {
        return historicStationCommodityEntityMapper.findById(entity.getStationId(), entity.getCommodityId(), entity.getTimestamp());
    }

    @Override
    public HistoricStationCommodityEntity create(HistoricStationCommodityEntity entity) {
        historicStationCommodityEntityMapper.insert(entity);

        return getById(entity)
                .orElseThrow(() -> new RuntimeException("historicStationCommodity with id: " + getIdString(entity) + " could not be found after create"));
    }

    private String getIdString(HistoricStationCommodityEntity entity) {
        return String.format("[stationId: '%s', commodityId: '%s', timestamp: '%s']", entity.getStationId(), entity.getCommodityId(), entity.getTimestamp());
    }
}
