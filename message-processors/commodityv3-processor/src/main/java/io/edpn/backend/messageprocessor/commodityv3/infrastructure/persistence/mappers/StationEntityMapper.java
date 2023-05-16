package io.edpn.backend.messageprocessor.commodityv3.infrastructure.persistence.mappers;

import io.edpn.backend.messageprocessor.commodityv3.application.dto.persistence.StationEntity;
import io.edpn.backend.messageprocessor.infrastructure.persistence.util.UuidTypeHandler;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface StationEntityMapper {
    @Results(id = "StationEntityResult", value = {
            @Result(property = "id", column = "id", javaType = UUID.class, typeHandler = UuidTypeHandler.class),
            @Result(property = "name", column = "name"),
            @Result(property = "edMarketId", column = "ed_market_id"),
            @Result(property = "marketUpdatedAt", column = "market_updated_at", javaType = LocalDateTime.class),
            @Result(property = "hasCommodities", column = "has_commodities"),
            @Result(property = "stationTypeId", column = "station_type_id"),
            @Result(property = "prohibitedCommodityIds", column = "id", many = @Many(select = "findProhibitedCommodityIdsByStationId")),
            @Result(property = "economyEntityIdProportionMap", column = "id", many = @Many(select = "findEconomyProportionsByStationId")),
            @Result(property = "systemId", column = "system_id", javaType = UUID.class, typeHandler = UuidTypeHandler.class)
    })
    @Select("SELECT id, name, ed_market_id, market_updated_at, has_commodities, station_type_id, system_id FROM station WHERE id = #{stationId}")
    Optional<StationEntity> findById(@Param("stationId") UUID stationId);

    @ResultMap("StationEntityResult")
    @Select("SELECT id, name, ed_market_id, market_updated_at, has_commodities, station_type_id, system_id FROM station WHERE ed_market_id = #{marketId}")
    Optional<StationEntity> findByMarketId(@Param("marketId") long marketId);

    @ResultMap("StationEntityResult")
    @Select("SELECT id, name, ed_market_id, market_updated_at, has_commodities, station_type_id, system_id FROM station WHERE system_id = #{systemId} and name = #{stationName}")
    Optional<StationEntity> findBySystemIdAndStationName(@Param("systemId") UUID systemId,@Param("stationName") String stationName);

    @Insert("INSERT INTO station (id, name, ed_market_id, market_updated_at, has_commodities, station_type_id, system_id) VALUES (#{id}, #{name}, #{edMarketId}, #{marketUpdatedAt}, #{hasCommodities}, #{systemId})")
    int insert(StationEntity stationEntity);

    @Update("UPDATE station SET name = #{name}, ed_market_id = #{edMarketId}, market_updated_at = #{marketUpdatedAt}, has_commodities = #{hasCommodities},  station_type_id=#{stationTypeId}, system_id = #{systemId} WHERE id = #{id}")
    int update(StationEntity stationEntity);

}
