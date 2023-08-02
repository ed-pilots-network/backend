package io.edpn.backend.exploration.adapter.persistence;

import io.edpn.backend.exploration.adapter.persistence.entity.SystemCoordinateRequestEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

public interface MybatisSystemCoordinateRequestRepository {

    @Insert("INSERT INTO system_coordinate_data_request (requesting_module, system_name) VALUES (#{requestingModule}, #{systemName})")
    void insert(SystemCoordinateRequestEntity requestDataMessageEntity);

    @Delete("DELETE FROM system_coordinate_data_request WHERE requesting_module = #{requestingModule} AND system_name = #{systemName}")
    void delete(SystemCoordinateRequestEntity requestDataMessageEntity);

    @Select("SELECT * FROM system_coordinate_data_request")
    List<SystemCoordinateRequestEntity> findAll();

    @Select("SELECT * FROM system_coordinate_data_request WHERE requesting_module = #{requestingModule} AND system_name = #{systemName}")
    Optional<SystemCoordinateRequestEntity> find(SystemCoordinateRequestEntity requestDataMessageEntity);

    @Select("SELECT * FROM system_coordinate_data_request WHERE system_name = #{systemName}")
    List<SystemCoordinateRequestEntity> findBySystemName(String requestDataMessageEntity);
}
