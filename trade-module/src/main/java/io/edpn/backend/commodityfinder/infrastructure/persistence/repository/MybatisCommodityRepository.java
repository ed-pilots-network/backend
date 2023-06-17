package io.edpn.backend.commodityfinder.infrastructure.persistence.repository;

import io.edpn.backend.commodityfinder.domain.model.Commodity;
import io.edpn.backend.commodityfinder.domain.repository.CommodityRepository;
import io.edpn.backend.commodityfinder.infrastructure.persistence.mappers.entity.CommodityMapper;
import io.edpn.backend.commodityfinder.infrastructure.persistence.mappers.mybatis.CommodityEntityMapper;
import io.edpn.backend.util.IdGenerator;
import io.edpn.backend.util.exception.DatabaseEntityNotFoundException;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class MybatisCommodityRepository implements CommodityRepository {

    private final IdGenerator idGenerator;
    private final CommodityMapper commodityMapper;
    private final CommodityEntityMapper commodityEntityMapper;

    @Override
    public Commodity findOrCreateByName(String name) {
        return commodityEntityMapper.findByName(name)
                .map(commodityMapper::map)
                .orElseGet(() -> {
                    Commodity s = Commodity.builder()
                            .name(name)
                            .build();
                    return create(s);
                });
    }

    @Override
    public Commodity create(Commodity commodity) {
        var entity = commodityMapper.map(commodity);
        if (Objects.isNull(entity.getId())) {
            entity.setId(idGenerator.generateId());
        }
        commodityEntityMapper.insert(entity);
        return findById(entity.getId())
                .orElseThrow(() -> new DatabaseEntityNotFoundException("commodity with id: " + commodity.getId() + " could not be found after create"));
    }

    @Override
    public Optional<Commodity> findById(UUID id) {
        return commodityEntityMapper.findById(id)
                .map(commodityMapper::map);
    }
}
