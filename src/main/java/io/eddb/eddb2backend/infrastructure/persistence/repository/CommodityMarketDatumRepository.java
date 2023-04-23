package io.eddb.eddb2backend.infrastructure.persistence.repository;

import io.eddb.eddb2backend.application.dto.persistence.CommodityMarketDatumEntity;
import io.eddb.eddb2backend.infrastructure.persistence.mappers.CommodityMarketDatumEntityMapper;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class CommodityMarketDatumRepository implements io.eddb.eddb2backend.domain.repository.CommodityMarketDatumRepository {

    private final CommodityMarketDatumEntityMapper commodityMarketDatumEntityMapper;

    @Override
    public CommodityMarketDatumEntity update(CommodityMarketDatumEntity entity) {
        commodityMarketDatumEntityMapper.update(entity);

        return getById(entity)
                .orElseThrow(() -> new RuntimeException("commodityMarketDatum with id: " + entity.getId() + " could not be found after update"));
    }

    @Override
    public Optional<CommodityMarketDatumEntity> getById(CommodityMarketDatumEntity entity) {
        return commodityMarketDatumEntityMapper.findById(entity.getId());
    }

    @Override
    public CommodityMarketDatumEntity create(CommodityMarketDatumEntity entity) {
        commodityMarketDatumEntityMapper.insert(entity);

        return getById(entity)
                .orElseThrow(() -> new RuntimeException("commodityMarketDatum with id: " + entity.getId() + " could not be found after create"));
    }
}
