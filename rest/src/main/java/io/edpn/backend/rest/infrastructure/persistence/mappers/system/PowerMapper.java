package io.edpn.backend.rest.infrastructure.persistence.mappers.system;

import io.edpn.backend.rest.domain.model.system.Power;
import io.edpn.backend.rest.infrastructure.util.UuidTypeHandler;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface PowerMapper {
    
    @Results(id = "PowerResult", value = {
            @Result(property = "id", column = "id", javaType = UUID.class, typeHandler = UuidTypeHandler.class),
            @Result(property = "name", column = "name")
    })
    @Select("Select * FROM powers WHERE id = #{id}")
    Optional<Power> findById(@Param("id") UUID id);
    
    @Results(id = "PowerResult")
    @Select("Select * FROM powers")
    List<Power> findAll();
    
    @ResultMap("PowerResult")
    @Select("Select * " +
            "FROM powers " +
            "WHERE name ILIKE #{nameSubString}" +
            "ORDER BY " +
            "CASE WHEN name ILIKE #{nameSubString} THEN 0 ELSE 1 END," +
            "name")
    List<Power> findByNameContains(@Param("nameSubString") String nameSubString);
}
