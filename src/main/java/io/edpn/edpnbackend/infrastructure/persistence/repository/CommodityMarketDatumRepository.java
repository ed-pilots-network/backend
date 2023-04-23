package io.edpn.edpnbackend.infrastructure.persistence.repository;

import io.edpn.edpnbackend.application.dto.persistence.CommodityMarketDatumEntity;
import io.edpn.edpnbackend.infrastructure.persistence.mappers.CommodityMarketDatumEntityMapper;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class CommodityMarketDatumRepository implements io.edpn.edpnbackend.domain.repository.CommodityMarketDatumRepository {

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
