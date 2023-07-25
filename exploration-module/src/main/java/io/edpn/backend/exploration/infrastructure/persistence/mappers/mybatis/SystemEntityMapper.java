package io.edpn.backend.exploration.infrastructure.persistence.mappers.mybatis;


import io.edpn.backend.exploration.infrastructure.persistence.entity.SystemEntity;
import io.edpn.backend.mybatisutil.UuidTypeHandler;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface SystemEntityMapper {

    @Select("SELECT * FROM system WHERE id = #{id}")
    @Results(id = "systemResultMap", value = {
            @Result(property = "id", column = "id", javaType = UUID.class, typeHandler = UuidTypeHandler.class),
            @Result(property = "name", column = "name", javaType = String.class),
            @Result(property = "starClass", column = "star_class", javaType = String.class),
            @Result(property = "eliteId", column = "elite_id", javaType = Long.class),
            @Result(property = "xCoordinate", column = "x_coordinate", javaType = Double.class),
            @Result(property = "yCoordinate", column = "y_coordinate", javaType = Double.class),
            @Result(property = "zCoordinate", column = "z_coordinate", javaType = Double.class)
    })
    Optional<SystemEntity> findById(UUID id);

    @Select("SELECT * FROM system WHERE name = #{name}")
    @ResultMap("systemResultMap")
    Optional<SystemEntity> findByName(@Param("name") String name);

    @Insert("INSERT INTO system (id, name, star_class, elite_id, x_coordinate, y_coordinate, z_coordinate) " +
            "VALUES (#{id}, #{name}, #{starClass}, #{eliteId}, #{xCoordinate}, #{yCoordinate}, #{zCoordinate})")
    void insert(SystemEntity system);

    @Update("UPDATE system SET name = #{name}, star_class = #{starClass}, elite_id = #{eliteId}, x_coordinate = #{xCoordinate}, " +
            "y_coordinate = #{yCoordinate}, z_coordinate = #{zCoordinate} WHERE id = #{id}")
    void update(SystemEntity system);

    @Delete("DELETE FROM system WHERE id = #{id}")
    void delete(@Param("id") UUID id);

    @Select("SELECT * FROM system WHERE name ILIKE CONCAT('%', #{name}, '%') ORDER BY CASE WHEN name ILIKE CONCAT(#{name}, '%') THEN 0 ELSE 1 END, name LIMIT #{amount}")
    @ResultMap("systemResultMap")
    List<SystemEntity> findFromSearchbar(@Param("name") String name, @Param("amount") int amount);
}
