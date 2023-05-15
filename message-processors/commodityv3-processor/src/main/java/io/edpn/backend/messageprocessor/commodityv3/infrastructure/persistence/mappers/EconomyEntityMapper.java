package io.edpn.backend.messageprocessor.commodityv3.infrastructure.persistence.mappers;

import io.edpn.backend.messageprocessor.commodityv3.application.dto.persistence.EconomyEntity;
import io.edpn.backend.messageprocessor.infrastructure.persistence.util.UuidTypeHandler;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface EconomyEntityMapper {

    @Results(id = "EconomyEntityResult", value = {
            @Result(property = "id", column = "id", javaType = UUID.class, typeHandler = UuidTypeHandler.class),
            @Result(property = "name", column = "name")
    })
    @Select("SELECT id, name FROM economy WHERE id = #{id}")
    Optional<EconomyEntity> findById(@Param("id") UUID id);

    @Insert("INSERT INTO economy (id, name) VALUES (#{id}, #{name})")
    int insert(EconomyEntity economyEntity);

    @ResultMap("EconomyEntityResult")
    @Select("SELECT id, name FROM economy WHERE name = #{name}")
    Optional<EconomyEntity> findByName(@Param("name") String name);
}
