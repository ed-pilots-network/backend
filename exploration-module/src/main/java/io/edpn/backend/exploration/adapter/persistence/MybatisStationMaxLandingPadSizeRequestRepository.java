package io.edpn.backend.exploration.adapter.persistence;

import io.edpn.backend.exploration.adapter.persistence.entity.MybatisStationMaxLandingPadSizeRequestEntity;
import io.edpn.backend.util.Module;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

public interface MybatisStationMaxLandingPadSizeRequestRepository {

    @Insert({
            "INSERT INTO station_maxlandingpadsize_data_request (requesting_module, system_name, station_name)",
            "VALUES (#{requestingModule}, #{systemName}, #{stationName})",
            "ON CONFLICT (requesting_module, system_name, station_name)",
            "DO NOTHING"
    })
    void insertIfNotExists(MybatisStationMaxLandingPadSizeRequestEntity requestDataMessageEntity);

    @Delete("DELETE FROM station_maxlandingpadsize_data_request WHERE requesting_module = #{requestingModule} AND system_name = #{systemName} AND station_name = #{stationName}")
    void delete(MybatisStationMaxLandingPadSizeRequestEntity requestDataMessageEntity);

    @Select("SELECT * FROM station_maxlandingpadsize_data_request")
    @ResultMap("stationMaxLandingPadSizeRequestResultMap")
    List<MybatisStationMaxLandingPadSizeRequestEntity> findAll();

    @Select("SELECT * FROM station_maxlandingpadsize_data_request WHERE system_name = #{systemName} AND station_name = #{stationName}")
    @Results(id = "stationMaxLandingPadSizeRequestResultMap", value = {
            @Result(property = "systemName", column = "system_name"),
            @Result(property = "stationName", column = "station_name"),
            @Result(property = "requestingModule", column = "requesting_module")
    })
    List<MybatisStationMaxLandingPadSizeRequestEntity> findByIdentifier(@Param("systemName") String systemName, @Param("stationName") String stationName);

    @Select("SELECT * FROM station_maxlandingpadsize_data_request WHERE requesting_module = #{requestingModule} AND system_name = #{systemName} AND station_name = #{stationName}")
    @ResultMap("stationMaxLandingPadSizeRequestResultMap")
    Optional<MybatisStationMaxLandingPadSizeRequestEntity> find(@Param("requestingModule") Module requestingModule, @Param("systemName") String systemName, @Param("stationName") String stationName);
}
