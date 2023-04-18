package io.eddb.eddb2backend.infrastructure.persistence.mybatis.mappers;

import io.eddb.eddb2backend.application.dto.persistence.CommodityEntity;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommodityMapper {

    int save(CommodityEntity commodityEntity);

    Optional<CommodityEntity> findById(UUID id);

    Collection<CommodityEntity> findByNameStartingWith(String name);

    void delete(UUID id);
}
