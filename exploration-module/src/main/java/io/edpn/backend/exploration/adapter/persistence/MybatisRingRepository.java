package io.edpn.backend.exploration.adapter.persistence;

import io.edpn.backend.exploration.adapter.persistence.entity.MybatisRingEntity;
import io.edpn.backend.mybatisutil.UuidTypeHandler;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.UUID;

public interface MybatisRingRepository {
    
    @Select({"INSERT INTO ring (id, inner_radius, mass, name, outer_radius, ring_class, body_id, star_id) " +
            "VALUES (#{id}, #{innerRadius}, #{mass}, #{name}, #{outerRadius}, #{ringClass}, #{bodyId}, #{starId}) " +
            "ON CONFLICT (name, body_id, star_id) " +
            "DO UPDATE SET " +
            "inner_radius = COALESCE(ring.inner_radius, EXCLUDED.inner_radius)," +
            "mass = COALESCE(ring.mass, EXCLUDED.mass)," +
            "outer_radius = COALESCE(ring.outer_radius, EXCLUDED.outer_radius)," +
            "ring_class = COALESCE(ring.ring_class, EXCLUDED.ring_class)," +
            "body_id = COALESCE(ring.body_id, EXCLUDED.body_id)," +
            "star_id = COALESCE(ring.star_id, EXCLUDED.star_id) " +
            "RETURNING *"})
    @ResultMap("ringResultMap")
    MybatisRingEntity insertOrUpdateOnConflict(MybatisRingEntity ring);
    
    @Select("SELECT * FROM ring WHERE body_id = #{id}")
    @Results(id = "ringResultMap", value = {
            @Result(property = "id", column = "id", javaType = UUID.class),
            @Result(property = "innerRadius", column = "inner_radius", javaType = Long.class),
            @Result(property = "mass", column = "mass", javaType = Long.class), // MT MegaTonnes
            @Result(property = "name", column = "name", javaType = String.class),
            @Result(property = "outerRadius", column = "outer_radius", javaType = Long.class),
            @Result(property = "ringClass", column = "ring_class", javaType = String.class),
            @Result(property = "bodyId", column = "body_id", javaType = UUID.class, typeHandler = UuidTypeHandler.class),
            @Result(property = "starId", column = "star_id", javaType = UUID.class, typeHandler = UuidTypeHandler.class)
    })
    List<MybatisRingEntity> findRingsByBodyId(UUID id);
    
    @Select("SELECT * FROM ring WHERE star_id = #{id}")
    @ResultMap("ringResultMap")
    List<MybatisRingEntity> findRingsByStarId(UUID id);
}

