package io.eddb.eddb2backend.infrastructure.persistence.mappers;

import io.eddb.eddb2backend.application.dto.persistence.StationCommodityEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StationCommodityEntityMapper {
    @Results(id = "StationCommodityResult", value = {
            @Result(property = "id.stationId.value", column = "station_id"),
            @Result(property = "id.commodityId.value", column = "commodity_id"),
            @Result(property = "meanPrice", column = "mean_price"),
            @Result(property = "buyPrice", column = "buy_price"),
            @Result(property = "stock", column = "stock"),
            @Result(property = "stockBracket", column = "stock_bracket"),
            @Result(property = "sellPrice", column = "sell_price"),
            @Result(property = "demand", column = "demand"),
            @Result(property = "demandBracket", column = "demand_bracket"),
            @Result(property = "statusFlags", column = "status_flags")
    })
    @Select("SELECT station_id, commodity_id, mean_price, buy_price, stock, stock_bracket, sell_price, demand, demand_bracket, status_flags FROM station_commodities")
    List<StationCommodityEntity> findAll();

    @ResultMap("StationCommodityResult")
    @Select("SELECT station_id, commodity_id, mean_price, buy_price, stock, stock_bracket, sell_price, demand, demand_bracket, status_flags FROM station_commodities WHERE station_id = #{stationId} AND commodity_id = #{commodityId}")
    Optional<StationCommodityEntity> findById(@Param("stationId") UUID stationId, @Param("commodityId") UUID commodityId);

    @ResultMap("StationCommodityResult")
    @Select("SELECT station_id, commodity_id, mean_price, buy_price, stock, stock_bracket, sell_price, demand, demand_bracket, status_flags FROM station_commodities WHERE station_id = #{stationId}")
    List<StationCommodityEntity> findByStationId(@Param("stationId") UUID stationId);

    @ResultMap("StationCommodityResult")
    @Select("SELECT station_id, commodity_id, mean_price, buy_price, stock, stock_bracket, sell_price, demand, demand_bracket, status_flags FROM station_commodities WHERE commodity_id = #{commodityId}")
    List<StationCommodityEntity> findByIdCommodityId(@Param("commodityId") UUID commodityId);

    @Insert("INSERT INTO station_commodities (station_id, commodity_id, mean_price, buy_price, stock, stock_bracket, sell_price, demand, demand_bracket, status_flags) VALUES (#{id.stationId.value}, #{id.commodityId.value}, #{meanPrice}, #{buyPrice}, #{stock}, #{stockBracket}, #{sellPrice}, #{demand}, #{demandBracket}, #{statusFlags})")
    int insert(StationCommodityEntity stationCommodityEntity);

    @Update("UPDATE station_commodities SET mean_price = #{meanPrice}, buy_price = #{buyPrice}, stock = #{stock}, stock_bracket = #{stockBracket}, sell_price = #{sellPrice}, demand = #{demand}, demand_bracket = #{demandBracket}, status_flags = #{statusFlags} WHERE station_id = #{id.stationId.value} AND commodity_id = #{id.commodityId.value}")
    int update(StationCommodityEntity stationCommodityEntity);

    @Delete("DELETE FROM station_commodities WHERE station_id = #{stationId} AND commodity_id = #{commodityId}")
    int deleteById(@Param("stationId") UUID stationId, @Param("commodityId") UUID commodityId);
    @Delete("DELETE FROM station_commodities WHERE station_id = #{stationId}")
    int deleteByIdStationId(@Param("stationId") UUID stationId);
}
