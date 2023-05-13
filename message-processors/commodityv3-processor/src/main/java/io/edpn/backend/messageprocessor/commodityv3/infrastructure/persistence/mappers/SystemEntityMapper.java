package io.edpn.backend.messageprocessor.commodityv3.infrastructure.persistence.mappers;

import io.edpn.backend.messageprocessor.commodityv3.application.dto.persistence.SystemEntity;
import io.edpn.backend.messageprocessor.infrastructure.persistence.util.UuidTypeHandler;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface SystemEntityMapper {

    @Results(id = "SystemEntityResult", value = {
            @Result(property = "id", column = "id", javaType = UUID.class, typeHandler = UuidTypeHandler.class),
            @Result(property = "name", column = "name")
    })
    @Select("SELECT id, name FROM system")
    List<SystemEntity> findAll();

    @ResultMap("SystemEntityResult")
    @Select("SELECT id, name FROM system WHERE id = #{id}")
    Optional<SystemEntity> findById(@Param("id") UUID id);

    @Insert("INSERT INTO system (id, name) VALUES (#{id}, #{name})")
    int insert(SystemEntity systemEntity);

    @Update("UPDATE system SET name = #{name} WHERE id = #{id}")
    int update(SystemEntity systemEntity);

    @Delete("DELETE FROM system WHERE id = #{id}")
    int delete(@Param("id") UUID id);

    @ResultMap("SystemEntityResult")
    @Select("SELECT id, name FROM system WHERE name = #{name}")
    Optional<SystemEntity> findByName(@Param("name") String name);

    @ResultMap("SystemEntityResult")
    @Select("SELECT id, name FROM system WHERE name LIKE #{namePrefix}%")
    List<SystemEntity> findByNameStartingWith(@Param("namePrefix") String namePrefix);

}
