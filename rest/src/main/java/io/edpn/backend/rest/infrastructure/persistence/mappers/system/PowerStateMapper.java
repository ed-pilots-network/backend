package io.edpn.backend.rest.infrastructure.persistence.mappers.system;

import io.edpn.backend.rest.domain.model.system.PowerState;
import io.edpn.backend.rest.infrastructure.util.UuidTypeHandler;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface PowerStateMapper {
    
    @Results(id = "PowerStateResult", value = {
            @Result(property = "id", column = "id", javaType = UUID.class, typeHandler = UuidTypeHandler.class),
            @Result(property = "name", column = "name")
    })
    @Select("Select * FROM power_states WHERE id = #{id}")
    Optional<PowerState> findById(@Param("id") UUID id);
    
    @Results(id = "PowerStateResult")
    @Select("Select * FROM power_states")
    List<PowerState> findAll();
    
    @ResultMap("PowerStateResult")
    @Select("Select * " +
            "FROM power_states " +
            "WHERE name ILIKE %#{nameSubString}%" +
            "ORDER BY " +
            "CASE WHEN name ILIKE #{nameSubString} THEN 0 ELSE 1 END," +
            "name")
    List<PowerState> findByNameContains(@Param("nameSubString") String nameSubString);
}
