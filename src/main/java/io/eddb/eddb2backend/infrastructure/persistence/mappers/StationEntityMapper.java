package io.eddb.eddb2backend.infrastructure.persistence.mappers;

import io.eddb.eddb2backend.application.dto.persistence.CommodityEntity;
import io.eddb.eddb2backend.application.dto.persistence.EconomyEntity;
import io.eddb.eddb2backend.application.dto.persistence.StationCommodityEntity;
import io.eddb.eddb2backend.application.dto.persistence.StationEntity;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.*;

public interface StationEntityMapper {

    @Results(id = "StationEntityResult", value = {
            @Result(property = "id.value", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "edMarketId", column = "ed_market_id"),
            @Result(property = "marketUpdatedAt", column = "market_updated_at", javaType = LocalDateTime.class),
            @Result(property = "hasCommodities", column = "has_commodities"),
            @Result(property = "prohibitedCommodityIds", column = "id", many = @Many(select = "findProhibitedCommodityIdsByStationId")),
            @Result(property = "economyEntityIdProportionMap", column = "id", many = @Many(select = "findEconomyProportionsByStationId")),
            @Result(property = "commodities", column = "id", many = @Many(select = "findStationCommodityIdsByStationId")),
    })
    @Select("SELECT id, name, ed_market_id, market_updated_at, has_commodities FROM stations")
    List<StationEntity> findAll();

    // Add additional methods for handling the complex relationships.
    @Select("SELECT commodity_id FROM prohibited_commodities WHERE station_id = #{stationId}")
    List<CommodityEntity.Id> findProhibitedCommodityIdsByStationId(UUID stationId);

    @Select("SELECT economy_id, proportion FROM economy_proportions WHERE station_id = #{stationId}")
    List<Map.Entry<EconomyEntity.Id, Double>> findEconomyProportionsByStationId(UUID stationId);

    @Select("SELECT station_commodity_id FROM station_commodities WHERE station_id = #{stationId}")
    List<StationCommodityEntity.Id> findStationCommodityIdsByStationId(UUID stationId);

    @ResultMap("StationEntityResult")
    @Select("SELECT id, name, ed_market_id, market_updated_at, has_commodities FROM stations WHERE station_id = #{stationId}")
    List<StationEntity> findById(@Param("stationId") UUID stationId);

    @ResultMap("StationEntityResult")
    @Select("SELECT id, name, ed_market_id, market_updated_at, has_commodities FROM stations WHERE ed_market_id = #{marketId}")
    Optional<StationEntity> findByMarketId(@Param("marketId") long marketId);

    @Insert("INSERT INTO stations (id, name, ed_market_id, market_updated_at, has_commodities) VALUES (#{id.value}, #{name}, #{edMarketId}, #{marketUpdatedAt}, #{hasCommodities})")
    int insert(StationEntity stationEntity);

    @Update("UPDATE stations SET name = #{name}, ed_market_id = #{edMarketId}, market_updated_at = #{marketUpdatedAt}, has_commodities = #{hasCommodities} WHERE id = #{id.value}")
    int update(StationEntity stationEntity);

    @Delete("DELETE FROM stations WHERE id = #{id}")
    int deleteById(UUID id);


}
