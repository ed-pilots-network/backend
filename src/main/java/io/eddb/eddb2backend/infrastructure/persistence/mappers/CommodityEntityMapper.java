package io.eddb.eddb2backend.infrastructure.persistence.mappers;

import io.eddb.eddb2backend.application.dto.persistence.CommodityEntity;
import io.eddb.eddb2backend.infrastructure.persistence.util.UuidTypeHandler;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface CommodityEntityMapper {

    @Results(id = "CommodityEntityResult", value = {
            @Result(property = "id", column = "id", javaType = UUID.class, typeHandler = UuidTypeHandler.class),
            @Result(property = "name", column = "name")
    })
    @Select("SELECT id, name FROM commodities")
    List<CommodityEntity> findAll();

    @ResultMap("CommodityEntityResult")
    @Select("SELECT id, name FROM commodities WHERE id = #{id}")
    Optional<CommodityEntity> findById(@Param("id") UUID id);

    @Insert("INSERT INTO commodities (id, name) VALUES (#{id}, #{name})")
    int insert(CommodityEntity commodityEntity);

    @Update("UPDATE commodities SET name = #{name} WHERE id = #{id}")
    int update(CommodityEntity commodityEntity);

    @Delete("DELETE FROM commodities WHERE id = #{id}")
    int delete(@Param("id") UUID id);

    @ResultMap("CommodityEntityResult")
    @Select("SELECT id, name FROM commodities WHERE name = #{name}")
    Optional<CommodityEntity> findByName(@Param("name") String name);
}
