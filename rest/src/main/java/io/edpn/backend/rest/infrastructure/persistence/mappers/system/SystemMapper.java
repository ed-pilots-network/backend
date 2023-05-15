package io.edpn.backend.rest.infrastructure.persistence.mappers.system;

import io.edpn.backend.rest.domain.model.common.Government;
import io.edpn.backend.rest.domain.model.system.System;
import io.edpn.backend.rest.infrastructure.util.UuidTypeHandler;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface SystemMapper {
    
    @Results(id = "SystemResult", value = {
            @Result(property = "id", column = "id", javaType = UUID.class, typeHandler = UuidTypeHandler.class),
            @Result(property = "name", column = "name"),
            @Result(property = "coordinate.x", column = "x"),
            @Result(property = "coordinate.y", column = "y"),
            @Result(property = "coordinate.z", column = "z"),
            @Result(property = "population", column = "population"),
            @Result(property = "government", column = "government_id",
                    javaType = Government.class, one =@One(select = "io.edpn.backend.rest.infrastructure.persistence.mappers.common.GovernmentMapper.findById")),
            @Result(property = "allegiance", column = "allegiance_id",
                    javaType = Government.class, one =@One(select = "io.edpn.backend.rest.infrastructure.persistence.mappers.common.AllegianceMapper.findById")),
            @Result(property = "security", column = "security_id",
                    javaType = Government.class, one =@One(select = "io.edpn.backend.rest.infrastructure.persistence.mappers.system.SecurityMapper.findById")),
            @Result(property = "primaryEconomy", column = "economy_id",
                    javaType = Government.class, one =@One(select = "io.edpn.backend.rest.infrastructure.persistence.mappers.common.EconomyMapper.findById")),
            @Result(property = "power", column = "power_id",
                    javaType = Government.class, one =@One(select = "io.edpn.backend.rest.infrastructure.persistence.mappers.system.PowerMapper.findById")),
            @Result(property = "powerState", column = "power_state_id",
                    javaType = Government.class, one =@One(select = "io.edpn.backend.rest.infrastructure.persistence.mappers.system.PowerStateMapper.findById")),
            @Result(property = "needsPermit", column = "needs_permit"),
            @Result(property = "lastUpdated", column = "last_updated"),
            @Result(property = "controllingMinorFaction", column = "controlling_minor_faction_id",
                    javaType = Government.class, one =@One(select = "io.edpn.backend.rest.infrastructure.persistence.mappers.common.FactionMapper.findById")),
            @Result(property = "reserveType", column = "reserve_type_id",
                    javaType = Government.class, one =@One(select = "io.edpn.backend.rest.infrastructure.persistence.mappers.system.ReserveTypeMapper.findById")),
            @Result(property = "eliteId", column = "elite_id")
    })
    @Select("SELECT * FROM system WHERE id = #{id}")
    Optional<System> findById(@Param("id") UUID id);
    
    @ResultMap("SystemResult")
    @Select("Select * " +
            "FROM system " +
            "WHERE name ILIKE %#{nameSubString}%" +
            "ORDER BY " +
            "CASE WHEN name ILIKE #{nameSubString} THEN 0 ELSE 1 END," +
            "name")
    List<System> findByNameContains(@Param("nameSubString") String nameSubString);
}
