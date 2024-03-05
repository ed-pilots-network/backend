package io.edpn.backend.trade.adapter.persistence.repository;

import io.edpn.backend.trade.adapter.persistence.entity.StationDataRequestEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MybatisStationPlanetaryRequestRepository {

    @Insert("INSERT INTO station_planetary_data_request (system_name, station_name) VALUES (#{systemName}, #{stationName})")
    void insert(@Param("systemName") String systemName, @Param("stationName") String stationName);

    @Delete("DELETE FROM station_planetary_data_request WHERE system_name = #{systemName} AND station_Name = #{stationName}")
    void delete(@Param("systemName") String systemName, @Param("stationName") String stationName);

    @Select("SELECT EXISTS(SELECT 1 FROM station_planetary_data_request WHERE system_name = #{systemName} AND station_Name = #{stationName})")
    boolean exists(@Param("systemName") String systemName, @Param("stationName") String stationName);

    @Select("SELECT * FROM station_planetary_data_request")
    @Results(id = "stationDataRequestResultMap", value = {
            @Result(property = "stationName", column = "station_name", javaType = String.class),
            @Result(property = "systemName", column = "system_name", javaType = String.class)
    })
    List<StationDataRequestEntity> findAll();
}
