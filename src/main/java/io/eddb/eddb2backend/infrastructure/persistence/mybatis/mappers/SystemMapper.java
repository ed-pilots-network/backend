package io.eddb.eddb2backend.infrastructure.persistence.mybatis.mappers;

import io.eddb.eddb2backend.application.dto.persistence.SystemEntity;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SystemMapper {

    int save(SystemEntity entity);

    Optional<SystemEntity> findById(UUID id);

    Optional<SystemEntity> findByName(String name);

    Collection<SystemEntity> findByNameStartingWith(String name);

    void addStation(@Param("systemId") UUID systemId, @Param("stationId") UUID stationId);

}
