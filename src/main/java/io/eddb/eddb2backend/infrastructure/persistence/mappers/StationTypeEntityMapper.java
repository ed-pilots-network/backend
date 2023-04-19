package io.eddb.eddb2backend.infrastructure.persistence.mappers;

import io.eddb.eddb2backend.application.dto.persistence.StationEntity;
import io.eddb.eddb2backend.application.dto.persistence.StationTypeEntity;
import io.eddb.eddb2backend.application.dto.persistence.SystemEntity;
import org.apache.ibatis.annotations.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StationTypeEntityMapper {

    @Results(id = "StationTypeEntityResult", value = {
            @Result(property = "id.value", column = "id"),
            @Result(property = "name", column = "name")
    })
    @Select("SELECT id, name FROM Stationtypes")
    List<StationTypeEntity> findAll();

    @ResultMap("StationTypeEntityResult")
    @Select("SELECT id, name FROM Stationtypes WHERE id = #{id}")
    Optional<StationTypeEntity> findById(@Param("id") UUID id);

    @Insert("INSERT INTO Stationtypes (id, name) VALUES (#{id.value}, #{name})")
    int insert(StationTypeEntity stationTypeEntity);

    @Update("UPDATE Stationtypes SET name = #{name} WHERE id = #{id.value}")
    int update(StationTypeEntity stationTypeEntity);

    @Delete("DELETE FROM Stationtypes WHERE id = #{id}")
    int delete(@Param("id") UUID id);

    @ResultMap("StationTypeEntityResult")
    @Select("SELECT id, name FROM Stationtypes WHERE name = #{name}")
    Optional<StationTypeEntity> findByName(@Param("name") String name);

    @ResultMap("StationTypeEntityResult")
    @Select("SELECT id, name FROM Stationtypes WHERE name LIKE #{namePrefix}%")
    List<StationTypeEntity> findByNameStartingWith(@Param("namePrefix") String namePrefix);
}
