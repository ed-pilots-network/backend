package io.edpn.backend.modulith.commodityfinder.infrastructure.persistence.mappers;

import io.edpn.backend.modulith.commodityfinder.application.dto.persistence.CommodityEntity;
import io.edpn.backend.modulith.mybatisutil.UuidTypeHandler;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Optional;
import java.util.UUID;

@Mapper
public interface CommodityEntityMapper {

    @Select("SELECT * FROM commodity WHERE id = #{id}")
    @Results(id = "commodityResultMap", value = {
            @Result(property = "id", column = "id", javaType = UUID.class, typeHandler = UuidTypeHandler.class),
            @Result(property = "name", column = "name", javaType = String.class)
    })
    @ResultMap("commodityResultMap")
    Optional<CommodityEntity> findById(UUID id);

    @Insert("INSERT INTO commodity (id, name) VALUES (#{id}, #{name})")
    void insert(CommodityEntity commodity);

    @Update("UPDATE commodity SET name = #{name} WHERE id = #{id}")
    void update(CommodityEntity commodity);

    @Delete("DELETE FROM commodity WHERE id = #{id}")
    void delete(UUID id);
}