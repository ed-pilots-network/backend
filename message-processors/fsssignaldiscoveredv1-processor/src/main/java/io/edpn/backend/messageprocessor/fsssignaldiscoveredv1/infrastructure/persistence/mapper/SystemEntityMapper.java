package io.edpn.backend.messageprocessor.fsssignaldiscoveredv1.infrastructure.persistence.mapper;

import io.edpn.backend.messageprocessor.fsssignaldiscoveredv1.application.dto.persistence.SystemEntity;
import io.edpn.backend.messageprocessor.infrastructure.persistence.util.UuidTypeHandler;
import java.util.Optional;
import java.util.UUID;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface SystemEntityMapper {

    @Results(id = "SystemEntityResult", value = {
            @Result(property = "id", column = "id", javaType = UUID.class, typeHandler = UuidTypeHandler.class),
            @Result(property = "name", column = "name"),
            @Result(property = "eliteId", column = "elite_id"),
            @Result(property = "xCoordinate", column = "x_coordinate"),
            @Result(property = "yCoordinate", column = "y_coordinate"),
            @Result(property = "zCoordinate", column = "z_coordinate")
    })
    @Select("SELECT id, name, elite_id, x_coordinate, y_coordinate, z_coordinate FROM system WHERE id = #{id}")
    Optional<SystemEntity> findById(@Param("id") UUID id);

    @Insert("INSERT INTO system (id, name, elite_id, x_coordinate, y_coordinate, z_coordinate) VALUES (#{id}, #{name}, #{eliteId}, #{xCoordinate}, #{yCoordinate}, #{zCoordinate})")
    int insert(SystemEntity systemEntity);

    @Update("UPDATE system SET name = #{name}, elite_id = #{eliteId}, x_coordinate = #{xCoordinate}, y_coordinate = #{yCoordinate}, z_coordinate = #{zCoordinate} WHERE id = #{id}")
    int update(SystemEntity systemEntity);

    @ResultMap("SystemEntityResult")
    @Select("SELECT id, name, elite_id, x_coordinate, y_coordinate, z_coordinate FROM system WHERE name = #{name}")
    Optional<SystemEntity> findByName(@Param("name") String name);

}
