package io.edpn.backend.modulith.commodityfinder.infrastructure.persistence.mappers;

import io.edpn.backend.modulith.commodityfinder.application.dto.persistence.CommodityEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface CommodityEntityMapper {

    @Select("SELECT * FROM commodity WHERE id = #{id}")
    Optional<CommodityEntity> findById(@Param("id") UUID id);

    @Select("SELECT * FROM commodity WHERE name = #{name}")
    Optional<CommodityEntity> findByName(@Param("name") String name);

    @Select("SELECT * FROM commodity")
    List<CommodityEntity> findAll();

    @Insert("INSERT INTO commodity (id, name) VALUES (#{id}, #{name})")
    void insert(CommodityEntity commodity);

    @Update("UPDATE commodity SET name = #{name} WHERE id = #{id}")
    void update(CommodityEntity commodity);

    @Delete("DELETE FROM commodity WHERE id = #{id}")
    void deleteById(@Param("id") UUID id);
}
