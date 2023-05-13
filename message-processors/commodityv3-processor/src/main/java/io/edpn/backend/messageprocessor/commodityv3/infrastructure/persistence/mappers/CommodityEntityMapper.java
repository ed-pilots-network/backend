package io.edpn.backend.messageprocessor.commodityv3.infrastructure.persistence.mappers;

import io.edpn.backend.messageprocessor.commodityv3.application.dto.persistence.CommodityEntity;
import io.edpn.backend.messageprocessor.infrastructure.persistence.util.UuidTypeHandler;
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
    @Select("SELECT id, name FROM commodity")
    List<CommodityEntity> findAll();

    @ResultMap("CommodityEntityResult")
    @Select("SELECT id, name FROM commodity WHERE id = #{id}")
    Optional<CommodityEntity> findById(@Param("id") UUID id);

    @Insert("INSERT INTO commodity (id, name) VALUES (#{id}, #{name})")
    int insert(CommodityEntity commodityEntity);

    @Update("UPDATE commodity SET name = #{name} WHERE id = #{id}")
    int update(CommodityEntity commodityEntity);

    @Delete("DELETE FROM commodity WHERE id = #{id}")
    int delete(@Param("id") UUID id);

    @ResultMap("CommodityEntityResult")
    @Select("SELECT id, name FROM commodity WHERE name = #{name}")
    Optional<CommodityEntity> findByName(@Param("name") String name);
}
