package io.edpn.backend.trade.infrastructure.persistence.mappers.mybatis;

import io.edpn.backend.trade.infrastructure.persistence.entity.CommodityEntity;
import io.edpn.backend.mybatisutil.UuidTypeHandler;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommodityEntityMapper {

    @Select("SELECT * FROM commodity")
    @Results(id = "commodityResultMap", value = {
            @Result(property = "id", column = "id", javaType = UUID.class, typeHandler = UuidTypeHandler.class),
            @Result(property = "name", column = "name", javaType = String.class)
    })
    List<CommodityEntity> findAll();

    @Select("SELECT * FROM commodity WHERE id = #{id}")
    @ResultMap("commodityResultMap")
    Optional<CommodityEntity> findById(@Param("id") UUID id);

    @Select("SELECT * FROM commodity WHERE name = #{name}")
    @ResultMap("commodityResultMap")
    Optional<CommodityEntity> findByName(@Param("name") String name);

    @Insert("INSERT INTO commodity (id, name) VALUES (#{id}, #{name})")
    void insert(CommodityEntity commodity);

    @Update("UPDATE commodity SET name = #{name} WHERE id = #{id}")
    void update(CommodityEntity commodity);

    @Delete("DELETE FROM commodity WHERE id = #{id}")
    void delete(@Param("id") UUID id);
}
