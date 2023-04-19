package io.eddb.eddb2backend.infrastructure.persistence.mappers;

import io.eddb.eddb2backend.application.dto.persistence.SystemEntity;
import io.eddb.eddb2backend.infrastructure.persistence.util.AbstractEntityIdTypeHandler;
import io.eddb.eddb2backend.infrastructure.persistence.util.StationTypeEntityIdTypeHandler;
import io.eddb.eddb2backend.infrastructure.persistence.util.SystemEntityIdTypeHandler;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SystemEntityMapper {

    @Results(id = "SystemEntityResult", value = {
            @Result(property = "id", column = "id", javaType = SystemEntity.Id.class, typeHandler = SystemEntityIdTypeHandler.class),
            @Result(property = "name", column = "name")
    })
    @Select("SELECT id, name FROM systems")
    List<SystemEntity> findAll();

    @ResultMap("SystemEntityResult")
    @Select("SELECT id, name FROM systems WHERE id = #{id}")
    Optional<SystemEntity> findById(@Param("id") UUID id);

    @Insert("INSERT INTO systems (id, name) VALUES (#{id.value}, #{name})")
    int insert(SystemEntity systemEntity);

    @Update("UPDATE systems SET name = #{name} WHERE id = #{id.value}")
    int update(SystemEntity systemEntity);

    @Delete("DELETE FROM systems WHERE id = #{id}")
    int delete(@Param("id") UUID id);

    @ResultMap("SystemEntityResult")
    @Select("SELECT id, name FROM systems WHERE name = #{name}")
    Optional<SystemEntity> findByName(@Param("name") String name);

    @ResultMap("SystemEntityResult")
    @Select("SELECT id, name FROM systems WHERE name LIKE #{namePrefix}%")
    List<SystemEntity> findByNameStartingWith(@Param("namePrefix") String namePrefix);

}
