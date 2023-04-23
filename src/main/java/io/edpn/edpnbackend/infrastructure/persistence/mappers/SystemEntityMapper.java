package io.edpn.edpnbackend.infrastructure.persistence.mappers;

import io.edpn.edpnbackend.application.dto.persistence.SystemEntity;
import io.edpn.edpnbackend.infrastructure.persistence.util.UuidTypeHandler;
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
    @Select("SELECT id, name FROM systems")
    List<SystemEntity> findAll();

    @ResultMap("SystemEntityResult")
    @Select("SELECT id, name FROM systems WHERE id = #{id}")
    Optional<SystemEntity> findById(@Param("id") UUID id);

    @Insert("INSERT INTO systems (id, name) VALUES (#{id}, #{name})")
    int insert(SystemEntity systemEntity);

    @Update("UPDATE systems SET name = #{name} WHERE id = #{id}")
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
