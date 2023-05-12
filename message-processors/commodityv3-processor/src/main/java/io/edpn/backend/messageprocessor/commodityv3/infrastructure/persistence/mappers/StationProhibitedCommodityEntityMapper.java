package io.edpn.backend.messageprocessor.commodityv3.infrastructure.persistence.mappers;

import io.edpn.backend.messageprocessor.commodityv3.application.dto.persistence.StationProhibitedCommodityEntity;
import io.edpn.backend.messageprocessor.infrastructure.persistence.util.UuidTypeHandler;
import org.apache.ibatis.annotations.*;

import java.util.Collection;
import java.util.UUID;

@Mapper
public interface StationProhibitedCommodityEntityMapper {

    @Results(id = "StationProhibitedCommodityEntityResult", value = {
            @Result(property = "stationId", column = "station_id", javaType = UUID.class, typeHandler = UuidTypeHandler.class),
            @Result(property = "commodityId", column = "commodity_id", javaType = UUID.class, typeHandler = UuidTypeHandler.class)
    })
    @Select("SELECT station_id, commodity_id FROM station_prohibited_commodities WHERE station_id = #{stationId}")
    Collection<StationProhibitedCommodityEntity> findByStationIds(@Param("stationId") UUID stationId);

    @Insert("INSERT INTO station_prohibited_commodities (station_id, commodity_id) VALUES (#{stationId}, #{commodityId})")
    int insert(StationProhibitedCommodityEntity entity);

    @Delete("DELETE FROM station_prohibited_commodities WHERE station_id = #{stationId}")
    int delete(@Param("stationId") UUID stationId);
}
