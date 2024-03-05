package io.edpn.backend.exploration.adapter.persistence;

import io.edpn.backend.exploration.adapter.persistence.entity.MybatisStationArrivalDistanceRequestEntity;
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

public interface MybatisStationArrivalDistanceRequestRepository {
    
    @Insert({
            "INSERT INTO station_arrivaldistance_data_request (requesting_module, system_name, station_name)",
            "VALUES (#{requestingModule}, #{systemName}, #{stationName})",
            "ON CONFLICT (requesting_module, system_name, station_name)",
            "DO NOTHING"
    })
    void insertIfNotExists(MybatisStationArrivalDistanceRequestEntity stationArrivalDistanceRequestEntity);
    
    @Delete("DELETE FROM station_arrivaldistance_data_request WHERE requesting_module = #{requestingModule} AND system_name = #{systemName} AND station_name = #{stationName}")
    void delete(MybatisStationArrivalDistanceRequestEntity stationArrivalDistanceRequestEntity);
    
    @Select("SELECT * FROM station_arrivaldistance_data_request")
    @ResultMap("stationArrivalDistanceRequestResultMap")
    List<MybatisStationArrivalDistanceRequestEntity> findAll();
    
    @Select("SELECT * FROM station_arrivaldistance_data_request WHERE system_name = #{systemName} AND station_name = #{stationName}")
    @Results(id = "stationArrivalDistanceRequestResultMap", value = {
            @Result(property = "systemName", column = "system_name"),
            @Result(property = "stationName", column = "station_name"),
            @Result(property = "requestingModule", column = "requesting_module")
    })
    List<MybatisStationArrivalDistanceRequestEntity> findByIdentifier(@Param("systemName") String systemName, @Param("stationName") String stationName);
    
    @Select("SELECT * FROM station_arrivaldistance_data_request WHERE requesting_module = #{requestingModule} AND system_name = #{systemName} AND station_name = #{stationName}")
    @ResultMap("stationArrivalDistanceRequestResultMap")
    Optional<MybatisStationArrivalDistanceRequestEntity> find(@Param("requestingModule") Module requestingModule, @Param("systemName") String systemName, @Param("stationName") String stationName);
}
