package io.eddb.eddb2backend.infrastructure.persistence.mappers;

import io.eddb.eddb2backend.application.dto.persistence.CommodityEntity;
import io.eddb.eddb2backend.application.dto.persistence.EconomyEntity;
import io.eddb.eddb2backend.application.dto.persistence.StationEntity;
import io.eddb.eddb2backend.infrastructure.persistence.util.UuidTypeHandler;
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
            @Result(property = "prohibitedCommodityIds", column = "id", many = @Many(select = "findProhibitedCommodityIdsByStationId")),
            @Result(property = "economyEntityIdProportionMap", column = "id", many = @Many(select = "findEconomyProportionsByStationId")),
    })
    @Select("SELECT id, name, ed_market_id, market_updated_at, has_commodities FROM stations")
    List<StationEntity> findAll();


    // Add additional methods for handling the complex relationships.
    @Select("SELECT commodity_id FROM station_prohibited_commodities WHERE station_id = #{stationId}")
    List<UUID> findProhibitedCommodityIdsByStationId(UUID stationId);

    @Select("SELECT economy_id, proportion FROM station_economy_proportions WHERE station_id = #{stationId}")
    List<Map.Entry<UUID, Double>> findEconomyProportionsByStationId(UUID stationId);

    @ResultMap("StationEntityResult")
    @Select("SELECT id, name, ed_market_id, market_updated_at, has_commodities FROM stations WHERE id = #{stationId}")
    Optional<StationEntity> findById(@Param("stationId") UUID stationId);

    @ResultMap("StationEntityResult")
    @Select("SELECT id, name, ed_market_id, market_updated_at, has_commodities FROM stations WHERE ed_market_id = #{marketId}")
    Optional<StationEntity> findByMarketId(@Param("marketId") long marketId);

    @Insert("INSERT INTO stations (id, name, ed_market_id, market_updated_at, has_commodities) VALUES (#{id}, #{name}, #{edMarketId}, #{marketUpdatedAt}, #{hasCommodities})")
    int insert(StationEntity stationEntity);

    @Update("UPDATE stations SET name = #{name}, ed_market_id = #{edMarketId}, market_updated_at = #{marketUpdatedAt}, has_commodities = #{hasCommodities} WHERE id = #{id}")
    int update(StationEntity stationEntity);

    @Delete("DELETE FROM stations WHERE id = #{id}")
    int deleteById(UUID id);

}
