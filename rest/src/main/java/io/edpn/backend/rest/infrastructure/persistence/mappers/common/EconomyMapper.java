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
    @Select("Select * FROM economys WHERE id = #{id}")
    Optional<Economy> findById(@Param("id") UUID id);
    
    @Results(id = "EconomyResult")
    @Select("Select * FROM economys")
    List<Economy> findAll();
    
    @Insert("INSERT INTO economys (id, name) VALUES (#{id}, #{name})")
    int insert(Economy powerEntity);
    
    @Update("UPDATE economys SET name = #{name} WHERE id = #{id}")
    int update(Economy powerEntity);
    
    @Delete("DELETE FROM economys WHERE id = #{id}")
    int delete(@Param("id") UUID id);
    
    @ResultMap("EconomyResult")
    @Select("Select * " +
            "FROM economys " +
            "WHERE name ILIKE #{nameSubString}" +
            "ORDER BY " +
            "CASE WHEN name ILIKE #{nameSubString} THEN 0 ELSE 1 END," +
            "name")
    List<Economy> findByNameContains(@Param("nameSubString") String nameSubString);
}
