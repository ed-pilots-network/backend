package io.edpn.backend.exploration.adapter.persistence;


import io.edpn.backend.exploration.adapter.persistence.entity.MybatisSystemEntity;
import io.edpn.backend.mybatisutil.UuidTypeHandler;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MybatisSystemRepository {

    @Select({"INSERT INTO system (id, name, star_class, elite_id, x_coordinate, y_coordinate, z_coordinate)",
            "VALUES (#{id}, #{name}, #{starClass}, #{eliteId}, #{xCoordinate}, #{yCoordinate}, #{zCoordinate})",
            "ON CONFLICT (name)",
            "DO UPDATE SET",
            "star_class = COALESCE(system.star_class, EXCLUDED.star_class),",
            "elite_id = COALESCE(system.elite_id, EXCLUDED.elite_id),",
            "x_coordinate = COALESCE(system.x_coordinate, EXCLUDED.x_coordinate),",
            "y_coordinate = COALESCE(system.y_coordinate, EXCLUDED.y_coordinate),",
            "z_coordinate = COALESCE(system.z_coordinate, EXCLUDED.z_coordinate)",
            "RETURNING *"
    })
    @ResultMap("systemResultMap")
    MybatisSystemEntity insertOrUpdateOnConflict(MybatisSystemEntity system);


    @Select("SELECT * FROM system WHERE name = #{name}")
    @Results(id = "systemResultMap", value = {
            @Result(property = "id", column = "id", javaType = UUID.class, typeHandler = UuidTypeHandler.class),
            @Result(property = "name", column = "name", javaType = String.class),
            @Result(property = "starClass", column = "star_class", javaType = String.class),
            @Result(property = "eliteId", column = "elite_id", javaType = Long.class),
            @Result(property = "xCoordinate", column = "x_coordinate", javaType = Double.class),
            @Result(property = "yCoordinate", column = "y_coordinate", javaType = Double.class),
            @Result(property = "zCoordinate", column = "z_coordinate", javaType = Double.class)
    })
    Optional<MybatisSystemEntity> findByName(@Param("name") String name);

    @Select({"SELECT *",
            "FROM system",
            "WHERE name ILIKE CONCAT('%', #{name}, '%')",
            "ORDER BY CASE WHEN name ILIKE CONCAT(#{name}, '%') THEN 0 ELSE 1 END, name",
            "LIMIT #{amount}"})
    @ResultMap("systemResultMap")
    List<MybatisSystemEntity> findFromSearchbar(@Param("name") String name, @Param("amount") int amount);
}
