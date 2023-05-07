package io.edpn.backend.messageprocessor.commodityv3.infrastructure.persistence.mappers;

import io.edpn.backend.messageprocessor.commodityv3.application.dto.persistence.StationStationTypeEntity;
import io.edpn.backend.messageprocessor.infrastructure.persistence.util.UuidTypeHandler;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface StationStationTypeEntityMapper {

    @Results(id = "StationStationTypeEntityResult", value = {
            @Result(property = "stationId", column = "station_id", javaType = UUID.class, typeHandler = UuidTypeHandler.class),
            @Result(property = "stationTypeId", column = "station_type_id", javaType = UUID.class, typeHandler = UuidTypeHandler.class)
    })
    @Select("SELECT station_id, station_type_id FROM station_station_types")
    List<StationStationTypeEntity> findAll();

    @ResultMap("StationStationTypeEntityResult")
    @Select("SELECT station_id, station_type_id FROM station_station_types WHERE station_id = #{stationId}")
    Optional<StationStationTypeEntity> findById(@Param("stationId") UUID stationId);

    @Insert("INSERT INTO station_station_types (station_id, station_type_id) VALUES (#{stationId}, #{systemId})")
    int insert(StationStationTypeEntity entity);

    @Update("UPDATE station_station_types SET station_type_id = #{stationTypeId} WHERE station_id = #{stationId}")
    int update(StationStationTypeEntity entity);

    @Delete("DELETE FROM station_station_types WHERE station_id = #{stationId}")
    int delete(@Param("stationId") UUID stationId);
}
