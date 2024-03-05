package io.edpn.backend.trade.adapter.persistence.repository;


import io.edpn.backend.mybatisutil.UuidTypeHandler;
import io.edpn.backend.trade.adapter.persistence.entity.SystemEntity;
import io.edpn.backend.trade.adapter.persistence.filter.FindSystemFilter;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MybatisSystemRepository {

    @Select({
            "SELECT id, name, elite_id, ST_X(coordinates_geom) as x_coordinate, ST_Y(coordinates_geom) as y_coordinate, ST_Z(coordinates_geom) as z_coordinate",
            "FROM system ",
            "WHERE id = #{id}"
    })
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

    @Insert({"INSERT INTO system (id, name, elite_id, coordinates_geom) ",
            "VALUES (#{id}, #{name}, #{eliteId}, ST_MakePoint(#{xCoordinate}, #{yCoordinate}, #{zCoordinate}))"})
    void insert(SystemEntity system);

    @Update({"UPDATE system",
            "SET name = #{name},",
            "elite_id = #{eliteId},",
            "coordinates_geom = ST_MakePoint(#{xCoordinate}, #{yCoordinate}, #{zCoordinate})",
            "WHERE id = #{id}"})
    void update(SystemEntity system);

    @Delete("DELETE FROM system WHERE id = #{id}")
    void delete(UUID id);

    @Select("""
            <script>
            SELECT id, name, elite_id, ST_X(coordinates_geom) as x_coordinate, ST_Y(coordinates_geom) as y_coordinate, ST_Z(coordinates_geom) as z_coordinate
            FROM system
            WHERE 1 = 1
            <if test='name != null'>AND name ILIKE '%' || #{name} || '%'</if>
            <if test='hasEliteId != null'>AND elite_id IS NULL != #{hasEliteId}</if>
            <if test='hasCoordinates != null'>AND coordinates_geom IS NULL != #{hasCoordinates}</if>
            </script>
            """)
    @ResultMap("systemResultMap")
    List<SystemEntity> findByFilter(FindSystemFilter map);

    @Select({"INSERT INTO system (id, name, elite_id, coordinates_geom)",
            "VALUES (#{id}, #{name}, #{eliteId}, ST_MakePoint(#{xCoordinate}, #{yCoordinate}, #{zCoordinate}))",
            "ON CONFLICT (name)",
            "DO UPDATE SET",
            "elite_id = COALESCE(system.elite_id, EXCLUDED.elite_id),",
            "coordinates_geom = COALESCE(system.coordinates_geom, EXCLUDED.coordinates_geom)",
            "RETURNING *"
    })
    @ResultMap("systemResultMap")
    SystemEntity createOrUpdateOnConflict(SystemEntity map);
}
