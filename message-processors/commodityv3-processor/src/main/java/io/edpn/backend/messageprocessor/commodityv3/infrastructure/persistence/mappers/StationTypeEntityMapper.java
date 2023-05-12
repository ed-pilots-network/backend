package io.edpn.backend.messageprocessor.commodityv3.infrastructure.persistence.mappers;

import io.edpn.backend.messageprocessor.commodityv3.application.dto.persistence.StationTypeEntity;
import io.edpn.backend.messageprocessor.infrastructure.persistence.util.UuidTypeHandler;
import org.apache.ibatis.annotations.*;

import java.util.Optional;
import java.util.UUID;

@Mapper
public interface StationTypeEntityMapper {

    @Results(id = "StationTypeEntityResult", value = {
            @Result(property = "id", column = "id", javaType = UUID.class, typeHandler = UuidTypeHandler.class),
            @Result(property = "name", column = "name")
    })
    @Select("SELECT id, name FROM station_types WHERE id = #{id}")
    Optional<StationTypeEntity> findById(@Param("id") UUID id);

    @Insert("INSERT INTO station_types (id, name) VALUES (#{id}, #{name})")
    int insert(StationTypeEntity stationTypeEntity);
}
