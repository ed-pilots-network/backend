package io.edpn.backend.rest.infrastructure.mappers;

import io.edpn.backend.rest.domain.model.common.Government;
import io.edpn.backend.rest.infrastructure.util.UuidTypeHandler;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface SystemMapper {
    
    static final String contentRoot = "";
    
    //TODO: Determine whether SystemResult should reference other mappers, or whether all methods should live in this Mapper
    @Results(id = "SystemResult", value = {
            @Result(property = "id", column = "id", javaType = UUID.class, typeHandler = UuidTypeHandler.class),
            @Result(property = "name", column = "name"),
            @Result(property = "coordinate.x", column = "x"),
            @Result(property = "coordinate.y", column = "y"),
            @Result(property = "coordinate.z", column = "z"),
            @Result(property = "population", column = "population"),
            @Result(property = "government", column = "government_id",
                    javaType = Government.class, one =@One(select = "io.edpn.backend.eddnrest.infrastructure.mappers.GovernmentMapper.findById"))
            
    })
    @Select("SELECT * FROM systems WHERE id = #{id}")
    Optional<System> findById(@Param("id") UUID id);

    @Insert("INSERT INTO systems (id, name) VALUES (#{id}, #{name})")
    int insert(System systemEntity);

    @Update("UPDATE systems SET name = #{name} WHERE id = #{id}")
    int update(System systemEntity);

    @Delete("DELETE FROM systems WHERE id = #{id}")
    int delete(@Param("id") UUID id);

    @ResultMap("SystemResult")
    @Select("SELECT id, name FROM systems WHERE name = #{name}")
    Optional<System> findByName(@Param("name") String name);

    @ResultMap("SystemResult")
    @Select("SELECT id, name FROM systems WHERE name LIKE #{namePrefix}%")
    List<System> findByNameStartingWith(@Param("namePrefix") String namePrefix);
}
