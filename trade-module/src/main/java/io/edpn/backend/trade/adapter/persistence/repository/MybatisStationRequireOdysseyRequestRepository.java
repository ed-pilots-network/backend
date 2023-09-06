package io.edpn.backend.trade.adapter.persistence.repository;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface MybatisStationRequireOdysseyRequestRepository {

    @Insert("INSERT INTO station_require_odyssey_data_request (system_name, station_name) VALUES (#{systemName}, #{stationName})")
    void insert(@Param("systemName") String systemName, @Param("stationName") String stationName);

    @Delete("DELETE FROM station_require_odyssey_data_request WHERE system_name = #{systemName} AND station_Name = #{stationName}")
    void delete(@Param("systemName") String systemName, @Param("stationName") String stationName);

    @Select("SELECT EXISTS(SELECT 1 FROM station_require_odyssey_data_request WHERE system_name = #{systemName} AND station_Name = #{stationName})")
    boolean exists(@Param("systemName") String systemName, @Param("stationName") String stationName);
}
