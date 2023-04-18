package io.eddb.eddb2backend.infrastructure.persistence.mybatis.mappers;

import io.eddb.eddb2backend.application.dto.persistence.StationEntity;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StationMapper {

    int save(StationEntity entity);

    Optional<StationEntity> findById(UUID id);

    Collection<StationEntity> findByNameStartingWith(String name);

    Optional<StationEntity> findByName(String name);
}
