package io.edpn.backend.rest.infrastructure.persistence.mappers.common;

import io.edpn.backend.rest.domain.model.common.Government;
import io.edpn.backend.rest.infrastructure.util.UuidTypeHandler;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface GovernmentMapper {
    
    @Results(id = "GovernmentResult", value = {
            @Result(property = "id", column = "id", javaType = UUID.class, typeHandler = UuidTypeHandler.class),
            @Result(property = "name", column = "name")
    })
    @Select("Select * FROM government WHERE id = #{id}")
    Optional<Government> findById(@Param("id") UUID id);
    
    @ResultMap("GovernmentResult")
    @Select("Select * FROM government")
    List<Government> findAll();
    
    @ResultMap("GovernmentResult")
    @Select("Select * " +
            "FROM government " +
            "WHERE name ILIKE %#{nameSubString}%" +
            "ORDER BY " +
            "CASE WHEN name ILIKE #{nameSubString} THEN 0 ELSE 1 END," +
            "name")
    List<Government> findByNameContains(@Param("nameSubString") String nameSubString);
}
