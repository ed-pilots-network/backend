package io.edpn.backend.rest.infrastructure.persistence.mappers.system;

import io.edpn.backend.rest.domain.model.system.ReserveType;
import io.edpn.backend.rest.infrastructure.util.UuidTypeHandler;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface ReserveTypeMapper {
    
    @Results(id = "ReserveTypeResult", value = {
            @Result(property = "id", column = "id", javaType = UUID.class, typeHandler = UuidTypeHandler.class),
            @Result(property = "name", column = "name")
    })
    @Select("Select * FROM reserve_types WHERE id = #{id}")
    Optional<ReserveType> findById(@Param("id") UUID id);
    
    @Results(id = "ReserveTypeResult")
    @Select("Select * FROM reserve_types")
    List<ReserveType> findAll();
    
    @ResultMap("ReserveTypeResult")
    @Select("Select * " +
            "FROM reserve_types " +
            "WHERE name ILIKE %#{nameSubString}%" +
            "ORDER BY " +
            "CASE WHEN name ILIKE #{nameSubString} THEN 0 ELSE 1 END," +
            "name")
    List<ReserveType> findByNameContains(@Param("nameSubString") String nameSubString);
}
