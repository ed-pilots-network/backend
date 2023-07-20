package io.edpn.backend.exploration.infrastructure.persistence.mappers.mybatis;

import io.edpn.backend.exploration.infrastructure.persistence.entity.SystemCoordinateDataRequestEntity;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface SystemCoordinateDataRequestEntityMapper {

    @Insert("INSERT INTO system_coordinate_data_request (requesting_module, system_name) VALUES (#{requestingModule}, #{systemName})")
    void insert(SystemCoordinateDataRequestEntity requestDataMessageEntity);

    @Delete("DELETE FROM system_coordinate_data_request WHERE requesting_module = #{requestingModule} AND system_name = #{systemName}")
    void delete(SystemCoordinateDataRequestEntity requestDataMessageEntity);

    @Select("SELECT * FROM system_coordinate_data_request")
    List<SystemCoordinateDataRequestEntity> findAll();

    @Select("SELECT * FROM system_coordinate_data_request WHERE requesting_module = #{requestingModule} AND system_name = #{systemName}")
    Optional<SystemCoordinateDataRequestEntity> find(SystemCoordinateDataRequestEntity requestDataMessageEntity);
}
