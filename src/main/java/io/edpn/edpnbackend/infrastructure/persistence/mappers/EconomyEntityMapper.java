package io.edpn.edpnbackend.infrastructure.persistence.mappers;

import io.edpn.edpnbackend.application.dto.persistence.EconomyEntity;
import io.edpn.edpnbackend.infrastructure.persistence.util.UuidTypeHandler;
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
    @Select("SELECT id, name FROM economies")
    List<EconomyEntity> findAll();

    @ResultMap("EconomyEntityResult")
    @Select("SELECT id, name FROM economies WHERE id = #{id}")
    Optional<EconomyEntity> findById(@Param("id") UUID id);

    @Insert("INSERT INTO economies (id, name) VALUES (#{id}, #{name})")
    int insert(EconomyEntity economyEntity);

    @Update("UPDATE economies SET name = #{name} WHERE id = #{id}")
    int update(EconomyEntity economyEntity);

    @Delete("DELETE FROM economies WHERE id = #{id}")
    int delete(@Param("id") UUID id);

    @ResultMap("EconomyEntityResult")
    @Select("SELECT id, name FROM economies WHERE name = #{name}")
    Optional<EconomyEntity> findByName(@Param("name") String name);
}
