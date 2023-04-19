package io.eddb.eddb2backend.infrastructure.persistence.mappers;

import io.eddb.eddb2backend.application.dto.persistence.CommodityEntity;
import io.eddb.eddb2backend.application.dto.persistence.EconomyEntity;
import io.eddb.eddb2backend.infrastructure.persistence.util.AbstractEntityIdTypeHandler;
import io.eddb.eddb2backend.infrastructure.persistence.util.CommodityEntityIdTypeHandler;
import io.eddb.eddb2backend.infrastructure.persistence.util.EconomyEntityIdTypeHandler;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EconomyEntityMapper {

    @Results(id = "EconomyEntityResult", value = {
            @Result(property = "id", column = "id", javaType = EconomyEntity.Id.class, typeHandler = EconomyEntityIdTypeHandler.class),
            @Result(property = "name", column = "name")
    })
    @Select("SELECT id, name FROM economies")
    List<EconomyEntity> findAll();

    @ResultMap("EconomyEntityResult")
    @Select("SELECT id, name FROM economies WHERE id = #{id}")
    Optional<EconomyEntity> findById(@Param("id") UUID id);

    @Insert("INSERT INTO economies (id, name) VALUES (#{id.value}, #{name})")
    int insert(EconomyEntity economyEntity);

    @Update("UPDATE economies SET name = #{name} WHERE id = #{id.value}")
    int update(EconomyEntity economyEntity);

    @Delete("DELETE FROM economies WHERE id = #{id}")
    int delete(@Param("id") UUID id);

    @ResultMap("EconomyEntityResult")
    @Select("SELECT id, name FROM economies WHERE name = #{name}")
    Optional<EconomyEntity> findByName(@Param("name") String name);
}
