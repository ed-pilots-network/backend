package io.edpn.backend.rest.infrastructure.persistence.mappers.common;

import io.edpn.backend.rest.domain.model.common.Faction;
import io.edpn.backend.rest.infrastructure.util.UuidTypeHandler;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface FactionMapper {
    
    @Results(id = "FactionResult", value = {
            @Result(property = "id", column = "id", javaType = UUID.class, typeHandler = UuidTypeHandler.class),
            @Result(property = "name", column = "name")
    })
    @Select("Select * FROM faction WHERE id = #{id}")
    Optional<Faction> findById(@Param("id") UUID id);
    
    @ResultMap("FactionResult")
    @Select("Select * FROM faction")
    List<Faction> findAll();
    
    @ResultMap("FactionResult")
    @Select("Select * " +
            "FROM faction " +
            "WHERE name ILIKE %#{nameSubString}%" +
            "ORDER BY " +
            "CASE WHEN name ILIKE #{nameSubString} THEN 0 ELSE 1 END," +
            "name")
    List<Faction> findByNameContains(@Param("nameSubString") String nameSubString);
}
