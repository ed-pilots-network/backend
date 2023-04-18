package io.eddb.eddb2backend.infrastructure.persistence.mybatis;

import io.eddb.eddb2backend.application.dto.persistence.CommodityEntity;
import io.eddb.eddb2backend.infrastructure.persistence.mybatis.mappers.CommodityMapper;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommodityRepository implements io.eddb.eddb2backend.domain.repository.CommodityRepository {

    private final CommodityMapper commodityMapper;

    public UUID save(CommodityEntity entity) {
        commodityMapper.save(entity);
        return entity.getId();
    }

    public Optional<CommodityEntity> findById(UUID id) {
        return commodityMapper.findById(id);
    }

    @Override
    public Collection<CommodityEntity> findByNameStartsWith(String name) {
        return commodityMapper.findByNameStartingWith(name);
    }

    @Override
    public void deleteById(UUID id) {
        commodityMapper.delete(id);
    }
}
