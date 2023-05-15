package io.edpn.backend.messageprocessor.commodityv3.infrastructure.persistence.repository;

import io.edpn.backend.messageprocessor.commodityv3.application.dto.persistence.CommodityEntity;
import io.edpn.backend.messageprocessor.commodityv3.infrastructure.persistence.mappers.CommodityEntityMapper;
import io.edpn.backend.messageprocessor.domain.exception.DatabaseEntityNotFoundException;
import io.edpn.backend.messageprocessor.domain.util.IdGenerator;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class CommodityRepository implements io.edpn.backend.messageprocessor.commodityv3.domain.repository.CommodityRepository {

    private final IdGenerator idGenerator;
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
    public CommodityEntity create(CommodityEntity entity) {
        if (Objects.isNull(entity.getId())) {
            entity.setId(idGenerator.generateId());
        }
        commodityEntityMapper.insert(entity);
        return findById(entity.getId())
                .orElseThrow(() -> new DatabaseEntityNotFoundException("commodity with id: " + entity.getId() + " could not be found after create"));
    }

    @Override
    public Optional<CommodityEntity> findById(UUID id) {
        return commodityEntityMapper.findById(id);
    }
}
