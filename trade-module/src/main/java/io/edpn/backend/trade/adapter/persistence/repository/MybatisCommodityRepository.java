package io.edpn.backend.trade.adapter.persistence.repository;

import io.edpn.backend.mybatisutil.UuidTypeHandler;
import io.edpn.backend.trade.adapter.persistence.entity.MybatisCommodityEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MybatisCommodityRepository {

    @Select("SELECT * FROM commodity")
    @Results(id = "commodityResultMap", value = {
            @Result(property = "id", column = "id", javaType = UUID.class, typeHandler = UuidTypeHandler.class),
            @Result(property = "name", column = "name", javaType = String.class)
    })
    List<MybatisCommodityEntity> findAll();

    @Select("SELECT * FROM commodity WHERE id = #{id}")
    @ResultMap("commodityResultMap")
    Optional<MybatisCommodityEntity> findById(@Param("id") UUID id);

    @Select("SELECT * FROM commodity WHERE name = #{name}")
    @ResultMap("commodityResultMap")
    Optional<MybatisCommodityEntity> findByName(@Param("name") String name);

    @Insert("INSERT INTO commodity (id, name) VALUES (#{id}, #{name})")
    void insert(MybatisCommodityEntity commodity);

    @Select({
            "INSERT INTO commodity (id, name)",
            "VALUES (#{id}, #{name})",
            "ON CONFLICT name",
            "DO UPDATE SET",
            "name = COALESCE(commodity.name, EXCLUDED.name)",
            "RETURNING *"
    })
    MybatisCommodityEntity createOrUpdateOnConflict(MybatisCommodityEntity commodity);
}
