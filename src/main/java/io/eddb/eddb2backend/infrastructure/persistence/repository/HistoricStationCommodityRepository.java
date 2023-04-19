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
                .orElseThrow(() -> new RuntimeException("historicStationCommodity with id: " + entity.getId() + " could not be found after update"));
    }

    @Override
    public Optional<HistoricStationCommodityEntity> getById(HistoricStationCommodityEntity entity) {
        return historicStationCommodityEntityMapper.findById(entity.getId().getStationId().getValue(), entity.getId().getCommodityId().getValue(), entity.getId().getTimestamp());
    }

    @Override
    public HistoricStationCommodityEntity create(HistoricStationCommodityEntity entity) {
        historicStationCommodityEntityMapper.insert(entity);

        return getById(entity)
                .orElseThrow(() -> new RuntimeException("historicStationCommodity with id: " + entity.getId() + " could not be found after create"));
    }
}
