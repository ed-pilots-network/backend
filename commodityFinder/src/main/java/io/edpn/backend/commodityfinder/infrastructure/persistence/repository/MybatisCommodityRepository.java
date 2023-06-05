package io.edpn.backend.commodityfinder.infrastructure.persistence.repository;

import io.edpn.backend.commodityfinder.infrastructure.persistence.mappers.CommodityEntityMapper;
import io.edpn.backend.commodityfinder.application.dto.persistence.CommodityEntity;
import io.edpn.backend.commodityfinder.domain.repository.CommodityRepository;
import io.edpn.backend.util.IdGenerator;
import io.edpn.backend.util.exception.DatabaseEntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class MybatisCommodityRepository implements CommodityRepository {

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
