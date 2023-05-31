package io.edpn.backend.modulith.commodityfinder.infrastructure.persistence.mappers;


import io.edpn.backend.modulith.commodityfinder.application.dto.persistence.SystemEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface SystemEntityMapper {

    @Select("SELECT * FROM system WHERE id = #{id}")
    Optional<SystemEntity> findById(@Param("id") UUID id);

    @Select("SELECT * FROM system WHERE name = #{name}")
    Optional<SystemEntity> findByName(@Param("name") String name);

    @Select("SELECT * FROM system")
    Collection<SystemEntity> findAll();

    @Insert("INSERT INTO system (id, name, elite_id, x_coordinate, y_coordinate, z_coordinate) " +
            "VALUES (#{id}, #{name}, #{eliteId}, #{xCoordinate}, #{yCoordinate}, #{zCoordinate})")
    void insert(SystemEntity system);

    @Update("UPDATE system SET name = #{name}, elite_id = #{eliteId}, x_coordinate = #{xCoordinate}, " +
            "y_coordinate = #{yCoordinate}, z_coordinate = #{zCoordinate} WHERE id = #{id}")
    void update(SystemEntity system);

    @Delete("DELETE FROM system WHERE id = #{id}")
    void deleteById(@Param("id") UUID id);
}