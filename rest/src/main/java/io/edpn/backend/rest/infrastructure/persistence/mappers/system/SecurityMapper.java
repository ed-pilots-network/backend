package io.edpn.backend.rest.infrastructure.persistence.mappers.system;

import io.edpn.backend.rest.domain.model.system.Security;
import io.edpn.backend.rest.infrastructure.util.UuidTypeHandler;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface SecurityMapper {
    
    @Results(id = "SecurityResult", value = {
            @Result(property = "id", column = "id", javaType = UUID.class, typeHandler = UuidTypeHandler.class),
            @Result(property = "name", column = "name")
    })
    @Select("Select * FROM security WHERE id = #{id}")
    Optional<Security> findById(@Param("id") UUID id);
    
    @ResultMap("SecurityResult")
    @Select("Select * FROM security")
    List<Security> findAll();
    
    @ResultMap("SecurityResult")
    @Select("Select * " +
            "FROM security " +
            "WHERE name ILIKE %#{nameSubString}%" +
            "ORDER BY " +
            "CASE WHEN name ILIKE #{nameSubString} THEN 0 ELSE 1 END," +
            "name")
    List<Security> findByNameContains(@Param("nameSubString") String nameSubString);
}
