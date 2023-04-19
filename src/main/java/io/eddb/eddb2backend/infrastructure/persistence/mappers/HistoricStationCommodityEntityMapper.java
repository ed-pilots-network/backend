package io.eddb.eddb2backend.infrastructure.persistence.mappers;

import io.eddb.eddb2backend.application.dto.persistence.HistoricStationCommodityEntity;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HistoricStationCommodityEntityMapper {

    @Results(id = "HistoricStationCommodityResult", value = {
            @Result(property = "id.stationId.value", column = "station_id"),
            @Result(property = "id.commodityId.value", column = "commodity_id"),
            @Result(property = "id.timestamp", column = "timestamp", javaType = LocalDateTime.class),
            @Result(property = "meanPrice", column = "mean_price"),
            @Result(property = "buyPrice", column = "buy_price"),
            @Result(property = "stock", column = "stock"),
            @Result(property = "stockBracket", column = "stock_bracket"),
            @Result(property = "sellPrice", column = "sell_price"),
            @Result(property = "demand", column = "demand"),
            @Result(property = "demandBracket", column = "demand_bracket"),
            @Result(property = "statusFlags", column = "status_flags")
    })
    @Select("SELECT station_id, commodity_id, timestamp, mean_price, buy_price, stock, stock_bracket, sell_price, demand, demand_bracket, status_flags FROM historic_station_commodities")
    List<HistoricStationCommodityEntity> findAll();

    @ResultMap("HistoricStationCommodityResult")
    @Select("SELECT station_id, commodity_id, timestamp, mean_price, buy_price, stock, stock_bracket, sell_price, demand, demand_bracket, status_flags FROM historic_station_commodities WHERE station_id = #{stationId} AND commodity_id = #{commodityId} AND timestamp = #{timestamp}")
    Optional<HistoricStationCommodityEntity> findById(@Param("stationId") UUID stationId, @Param("commodityId") UUID commodityId, @Param("timestamp") LocalDateTime timestamp);

    @ResultMap("HistoricStationCommodityResult")
    @Select("SELECT station_id, commodity_id, timestamp, mean_price, buy_price, stock, stock_bracket, sell_price, demand, demand_bracket, status_flags FROM historic_station_commodities WHERE commodity_id = #{commodityId} AND timestamp BETWEEN #{startTimestamp} AND #{endTimestamp}")
    List<HistoricStationCommodityEntity> findByIdCommodityIdAndTimestampBetween(@Param("commodityId") UUID commodityId, @Param("startTimestamp") LocalDateTime startTimestamp, @Param("endTimestamp") LocalDateTime endTimestamp);

    @ResultMap("HistoricStationCommodityResult")
    @Select("SELECT station_id, commodity_id, timestamp, mean_price, buy_price, stock, stock_bracket, sell_price, demand, demand_bracket, status_flags FROM historic_station_commodities WHERE station_id = #{stationId} AND timestamp BETWEEN #{startTimestamp} AND #{endTimestamp}")
    List<HistoricStationCommodityEntity> findByIdStationIdAndTimestampBetween(@Param("stationId") UUID stationId, @Param("startTimestamp") LocalDateTime startTimestamp, @Param("endTimestamp") LocalDateTime endTimestamp);

    @ResultMap("HistoricStationCommodityResult")
    @Select("SELECT station_id, commodity_id, timestamp, mean_price, buy_price, stock, stock_bracket, sell_price, demand, demand_bracket, status_flags FROM historic_station_commodities WHERE commodity_id = #{commodityId} AND timestamp = (SELECT MAX(timestamp) FROM historic_station_commodities WHERE commodity_id = #{commodityId})")
    List<HistoricStationCommodityEntity> findLatestByIdCommodityId(@Param("commodityId") UUID commodityId);

    @ResultMap("HistoricStationCommodityResult")
    @Select("SELECT station_id, commodity_id, timestamp, mean_price, buy_price, stock, stock_bracket, sell_price, demand, demand_bracket, status_flags FROM historic_station_commodities WHERE station_id = #{stationId} AND timestamp = (SELECT MAX(timestamp) FROM historic_station_commodities WHERE station_id = #{stationId})")
    List<HistoricStationCommodityEntity> findLatestByIdStationId(@Param("stationId") UUID stationId);

    @Insert("INSERT INTO historic_station_commodities (station_id, commodity_id, timestamp, mean_price, buy_price, stock, stock_bracket, sell_price, demand, demand_bracket, status_flags) VALUES (#{id.stationId.value}, #{id.commodityId.value}, #{id.timestamp}, #{meanPrice}, #{buyPrice}, #{stock}, #{stockBracket}, #{sellPrice}, #{demand}, #{demandBracket}, #{statusFlags})")
    int insert(HistoricStationCommodityEntity historicStationCommodityEntity);

    @Update("UPDATE historic_station_commodities SET mean_price = #{meanPrice}, buy_price = #{buyPrice}, stock = #{stock}, stock_bracket = #{stockBracket}, sell_price = #{sellPrice}, demand = #{demand}, demand_bracket = #{demandBracket}, status_flags = #{statusFlags} WHERE station_id = #{id.stationId.value} AND commodity_id = #{id.commodityId.value} AND timestamp = #{id.timestamp}")
    int update(HistoricStationCommodityEntity historicStationCommodityEntity);

    @Delete("DELETE FROM historic_station_commodities WHERE station_id = #{stationId} AND commodity_id = #{commodityId} AND timestamp = #{timestamp}")
    int deleteById(@Param("stationId") UUID stationId, @Param("commodityId") UUID commodityId, @Param("timestamp") LocalDateTime timestamp);
}
