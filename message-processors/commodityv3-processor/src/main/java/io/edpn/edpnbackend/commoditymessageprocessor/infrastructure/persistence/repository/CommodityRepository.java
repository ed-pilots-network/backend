package io.edpn.edpnbackend.commoditymessageprocessor.infrastructure.persistence.repository;

import io.edpn.edpnbackend.commoditymessageprocessor.application.dto.persistence.CommodityEntity;
import io.edpn.edpnbackend.commoditymessageprocessor.infrastructure.persistence.mappers.CommodityEntityMapper;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class CommodityRepository implements io.edpn.edpnbackend.commoditymessageprocessor.domain.repository.CommodityRepository {

    private final CommodityEntityMapper commodityEntityMapper;

    @Override
    public CommodityEntity findOrCreateByName(String name) {
        return commodityEntityMapper.findByName(name)
                .orElseGet(() -> {
                    CommodityEntity s = CommodityEntity.builder()
                            .name(name)
                            .build();
                    return create(s);
                });
    }

    @Override
    public CommodityEntity update(CommodityEntity commodity) {
        commodityEntityMapper.update(commodity);
        return findById(commodity.getId())
                .orElseThrow(() -> new RuntimeException("commodity with id: " + commodity.getId() + " could not be found after update"));
    }

    @Override
    public CommodityEntity create(CommodityEntity entity) {
        entity.setId(UUID.randomUUID());
        commodityEntityMapper.insert(entity);
        return findById(entity.getId())
                .orElseThrow(() -> new RuntimeException("commodity with id: " + entity.getId() + " could not be found after create"));
    }

    @Override
    public Optional<CommodityEntity> findById(UUID id) {
        return commodityEntityMapper.findById(id);
    }
}
