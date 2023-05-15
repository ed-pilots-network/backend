package io.edpn.backend.rest.infrastructure.persistence.mappers.common;

import io.edpn.backend.rest.domain.model.common.Allegiance;
import io.edpn.backend.rest.infrastructure.util.UuidTypeHandler;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface AllegianceMapper {
    
    @Results(id = "AllegianceResult", value = {
            @Result(property = "id", column = "id", javaType = UUID.class, typeHandler = UuidTypeHandler.class),
            @Result(property = "name", column = "name")
    })
    @Select("Select * FROM allegiance WHERE id = #{id}")
    Optional<Allegiance> findById(@Param("id") UUID id);
    
    @ResultMap("AllegianceResult")
    @Select("Select * FROM allegiance")
    List<Allegiance> findAll();
    
    @ResultMap("AllegianceResult")
    @Select("Select * " +
            "FROM allegiance " +
            "WHERE name ILIKE %#{nameSubString}%" +
            "ORDER BY " +
            "CASE WHEN name ILIKE #{nameSubString} THEN 0 ELSE 1 END," +
            "name")
    List<Allegiance> findByNameContains(@Param("nameSubString") String nameSubString);
}
