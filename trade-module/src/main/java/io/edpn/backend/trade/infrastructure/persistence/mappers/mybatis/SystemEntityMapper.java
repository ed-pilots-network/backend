package io.edpn.backend.trade.infrastructure.persistence.mappers.mybatis;


import io.edpn.backend.trade.infrastructure.persistence.entity.SystemEntity;
import io.edpn.backend.mybatisutil.UuidTypeHandler;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Optional;
import java.util.UUID;

public interface SystemEntityMapper {

    @Select("SELECT * FROM system WHERE id = #{id}")
    @Results(id = "systemResultMap", value = {
            @Result(property = "id", column = "id", javaType = UUID.class, typeHandler = UuidTypeHandler.class),
            @Result(property = "name", column = "name", javaType = String.class),
            @Result(property = "eliteId", column = "elite_id", javaType = Long.class),
            @Result(property = "xCoordinate", column = "x_coordinate", javaType = Double.class),
            @Result(property = "yCoordinate", column = "y_coordinate", javaType = Double.class),
            @Result(property = "zCoordinate", column = "z_coordinate", javaType = Double.class)
    })
    Optional<SystemEntity> findById(UUID id);

    @Select("SELECT * FROM system WHERE name = #{name}")
    @ResultMap("systemResultMap")
    Optional<SystemEntity> findByName(@Param("name") String name);

    @Insert("INSERT INTO system (id, name, elite_id, x_coordinate, y_coordinate, z_coordinate) " +
            "VALUES (#{id}, #{name}, #{eliteId}, #{xCoordinate}, #{yCoordinate}, #{zCoordinate})")
    void insert(SystemEntity system);

    @Update("UPDATE system SET name = #{name}, elite_id = #{eliteId}, x_coordinate = #{xCoordinate}, " +
            "y_coordinate = #{yCoordinate}, z_coordinate = #{zCoordinate} WHERE id = #{id}")
    void update(SystemEntity system);

    @Delete("DELETE FROM system WHERE id = #{id}")
    void delete(UUID id);
}
