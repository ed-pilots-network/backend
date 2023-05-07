package io.edpn.edpnbackend.commoditymessageprocessor.infrastructure.persistence.mappers;

import io.edpn.edpnbackend.commoditymessageprocessor.application.dto.persistence.HistoricStationCommodityMarketDatumEntity;
import io.edpn.backend.messageprocessor.infrastructure.persistence.util.UuidTypeHandler;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface HistoricStationCommodityMarketDatumEntityMapper {

    @Results(id = "HistoricStationCommodityMarketDatumResult", value = {
            @Result(property = "id", column = "id", javaType = UUID.class, typeHandler = UuidTypeHandler.class),
            @Result(property = "stationId", column = "station_id", javaType = UUID.class, typeHandler = UuidTypeHandler.class),
            @Result(property = "commodityId", column = "commodity_id", javaType = UUID.class, typeHandler = UuidTypeHandler.class),
            @Result(property = "timestamp", column = "timestamp", javaType = LocalDateTime.class),
            @Result(property = "meanPrice", column = "mean_price"),
            @Result(property = "buyPrice", column = "buy_price"),
            @Result(property = "stock", column = "stock"),
            @Result(property = "stockBracket", column = "stock_bracket"),
            @Result(property = "sellPrice", column = "sell_price"),
            @Result(property = "demand", column = "demand"),
            @Result(property = "demandBracket", column = "demand_bracket"),
            @Result(property = "statusFlags", column = "status_flags")
    })
    @Select("SELECT id, station_id, commodity_id, timestamp, mean_price, buy_price, stock, stock_bracket, sell_price, demand, demand_bracket, status_flags FROM historic_station_commodity_market_data")
    List<HistoricStationCommodityMarketDatumEntity> findAll();

    @ResultMap("HistoricStationCommodityMarketDatumResult")
    @Select("SELECT id, station_id, commodity_id, timestamp, mean_price, buy_price, stock, stock_bracket, sell_price, demand, demand_bracket, status_flags FROM historic_station_commodity_market_data WHERE station_id = #{stationId} AND commodity_id = #{commodityId} AND timestamp = #{timestamp}")
    Optional<HistoricStationCommodityMarketDatumEntity> findById(@Param("stationId") UUID stationId, @Param("commodityId") UUID commodityId, @Param("timestamp") LocalDateTime timestamp);

    @ResultMap("HistoricStationCommodityMarketDatumResult")
    @Select("SELECT id, station_id, commodity_id, timestamp, mean_price, buy_price, stock, stock_bracket, sell_price, demand, demand_bracket, status_flags FROM historic_station_commodity_market_data WHERE commodity_id = #{commodityId} AND timestamp BETWEEN #{startTimestamp} AND #{endTimestamp}")
    List<HistoricStationCommodityMarketDatumEntity> findByCommodityIdAndTimestampBetween(@Param("commodityId") UUID commodityId, @Param("startTimestamp") LocalDateTime startTimestamp, @Param("endTimestamp") LocalDateTime endTimestamp);

    @ResultMap("HistoricStationCommodityMarketDatumResult")
    @Select("SELECT id, station_id, commodity_id, timestamp, mean_price, buy_price, stock, stock_bracket, sell_price, demand, demand_bracket, status_flags FROM historic_station_commodity_market_data WHERE station_id = #{stationId} AND timestamp BETWEEN #{startTimestamp} AND #{endTimestamp}")
    List<HistoricStationCommodityMarketDatumEntity> findByIdStationIdAndTimestampBetween(@Param("stationId") UUID stationId, @Param("startTimestamp") LocalDateTime startTimestamp, @Param("endTimestamp") LocalDateTime endTimestamp);

    @ResultMap("HistoricStationCommodityMarketDatumResult")
    @Select("SELECT id, station_id, commodity_id, timestamp, mean_price, buy_price, stock, stock_bracket, sell_price, demand, demand_bracket, status_flags FROM historic_station_commodity_market_data WHERE commodity_id = #{commodityId} AND timestamp = (SELECT MAX(timestamp) FROM historic_station_commodity_market_data WHERE commodity_id = #{commodityId})")
    List<HistoricStationCommodityMarketDatumEntity> findLatestByIdCommodityId(@Param("commodityId") UUID commodityId);

    @ResultMap("HistoricStationCommodityMarketDatumResult")
    @Select("SELECT id, station_id, commodity_id, timestamp, mean_price, buy_price, stock, stock_bracket, sell_price, demand, demand_bracket, status_flags FROM historic_station_commodity_market_data WHERE station_id = #{stationId} AND timestamp = (SELECT MAX(timestamp) FROM historic_station_commodity_market_data WHERE station_id = #{stationId})")
    List<HistoricStationCommodityMarketDatumEntity> findLatestByIdStationId(@Param("stationId") UUID stationId);

    @Insert("INSERT INTO historic_station_commodity_market_data (id, station_id, commodity_id, timestamp, mean_price, buy_price, stock, stock_bracket, sell_price, demand, demand_bracket, status_flags) VALUES (#{id}, #{stationId}, #{commodityId}, #{timestamp}, #{meanPrice}, #{buyPrice}, #{stock}, #{stockBracket}, #{sellPrice}, #{demand}, #{demandBracket}, #{statusFlags})")
    int insert(HistoricStationCommodityMarketDatumEntity historicStationCommodityMarketDatumEntity);

    @Update("UPDATE historic_station_commodity_market_data SET station_id = #{stationId}, commodity_id = #{commodityId}, timestamp = #{timestamp}, mean_price = #{meanPrice}, buy_price = #{buyPrice}, stock = #{stock}, stock_bracket = #{stockBracket}, sell_price = #{sellPrice}, demand = #{demand}, demand_bracket = #{demandBracket}, status_flags = #{statusFlags} WHERE id = #{id} ")
    int update(HistoricStationCommodityMarketDatumEntity historicStationCommodityMarketDatumEntity);

    @Delete("DELETE FROM historic_station_commodity_market_data WHERE id = #{id}")
    int deleteById(@Param("id") UUID id);

    @Delete("""
            WITH cte AS (
                SELECT *,
                    ROW_NUMBER() OVER (PARTITION BY station_id, commodity_id ORDER BY timestamp) rn,
                    LAG(timestamp) OVER (PARTITION BY station_id, commodity_id ORDER BY timestamp) prev_timestamp,
                    LEAD(timestamp) OVER (PARTITION BY station_id, commodity_id ORDER BY timestamp) next_timestamp
                FROM historic_station_commodity_market_data
                WHERE station_id = #{stationId} AND commodity_id = #{commodityId}
            )
            DELETE FROM historic_station_commodity_market_data
            WHERE id IN (
                SELECT id
                FROM cte
                WHERE rn > 1
                  AND (next_timestamp IS NOT NULL OR prev_timestamp IS NOT NULL)
                  AND (next_timestamp = timestamp + INTERVAL '1 microsecond' OR prev_timestamp = timestamp - INTERVAL '1 microsecond')
            )
            """)
    int deleteObsoleteInbetweenValues(@Param("stationId") UUID stationId, @Param("commodityId") UUID commodityId);
}
