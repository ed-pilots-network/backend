package io.edpn.backend.rest.infrastructure.persistence.mappers.common;

import io.edpn.backend.rest.domain.model.common.Economy;
import io.edpn.backend.rest.infrastructure.util.UuidTypeHandler;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface EconomyMapper {
    
    @Results(id = "EconomyResult", value = {
            @Result(property = "id", column = "id", javaType = UUID.class, typeHandler = UuidTypeHandler.class),
            @Result(property = "name", column = "name")
    })
    @Select("Select * FROM economy WHERE id = #{id}")
    Optional<Economy> findById(@Param("id") UUID id);
    
    @ResultMap("EconomyResult")
    @Select("Select * FROM economy")
    List<Economy> findAll();
    
    @ResultMap("EconomyResult")
    @Select("Select * " +
            "FROM economy " +
            "WHERE name ILIKE %#{nameSubString}%" +
            "ORDER BY " +
            "CASE WHEN name ILIKE #{nameSubString} THEN 0 ELSE 1 END," +
            "name")
    List<Economy> findByNameContains(@Param("nameSubString") String nameSubString);
}
