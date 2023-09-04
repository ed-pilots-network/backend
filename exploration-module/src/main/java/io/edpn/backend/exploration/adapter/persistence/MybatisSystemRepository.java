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

    @Insert({"INSERT INTO system (id, name, star_class, elite_id, x_coordinate, y_coordinate, z_coordinate)",
            "VALUES (#{id}, #{name}, #{starClass}, #{eliteId}, #{xCoordinate}, #{yCoordinate}, #{zCoordinate})",
            "ON CONFLICT (name)",
            "DO UPDATE",
            "star_class = CASE WHEN system.star_class IS NULL THEN excluded.star_class ELSE system.star_class END,",
            "elite_id = CASE WHEN system.elite_id IS NULL THEN excluded.elite_id ELSE system.elite_id END,",
            "x_coordinate = CASE WHEN system.x_coordinate IS NULL THEN excluded.x_coordinate ELSE system.x_coordinate END,",
            "y_coordinate = CASE WHEN system.y_coordinate IS NULL THEN excluded.y_coordinate ELSE system.y_coordinate END,",
            "z_coordinate = CASE WHEN system.z_coordinate IS NULL THEN excluded.z_coordinate ELSE system.z_coordinate END",
            "RETURNING *"
    })
    @Results(id = "systemResultMap", value = {
            @Result(property = "id", column = "id", javaType = UUID.class, typeHandler = UuidTypeHandler.class),
            @Result(property = "name", column = "name", javaType = String.class),
            @Result(property = "starClass", column = "star_class", javaType = String.class),
            @Result(property = "eliteId", column = "elite_id", javaType = Long.class),
            @Result(property = "xCoordinate", column = "x_coordinate", javaType = Double.class),
            @Result(property = "yCoordinate", column = "y_coordinate", javaType = Double.class),
            @Result(property = "zCoordinate", column = "z_coordinate", javaType = Double.class)
    })
    MybatisSystemEntity insertOrUpdateOnConflict(MybatisSystemEntity system);


    @Select("SELECT * FROM system WHERE name = #{name}")
    @ResultMap("systemResultMap")
    Optional<MybatisSystemEntity> findByName(@Param("name") String name);

    @Select({"SELECT *",
            "FROM system",
            "WHERE name ILIKE CONCAT('%', #{name}, '%')",
            "ORDER BY CASE WHEN name ILIKE CONCAT(#{name}, '%') THEN 0 ELSE 1 END, name",
            "LIMIT #{amount}"})
    @ResultMap("systemResultMap")
    List<MybatisSystemEntity> findFromSearchbar(@Param("name") String name, @Param("amount") int amount);
}
