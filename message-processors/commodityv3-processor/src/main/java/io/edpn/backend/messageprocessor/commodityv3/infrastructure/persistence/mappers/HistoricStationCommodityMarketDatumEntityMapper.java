package io.edpn.backend.messageprocessor.commodityv3.infrastructure.persistence.mappers;

import io.edpn.backend.messageprocessor.commodityv3.application.dto.persistence.HistoricStationCommodityMarketDatumEntity;
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
    @Select("SELECT id, station_id, commodity_id, timestamp, mean_price, buy_price, stock, stock_bracket, sell_price, demand, demand_bracket, status_flags FROM historic_station_commodity_market_data WHERE station_id = #{stationId} AND commodity_id = #{commodityId} AND timestamp = #{timestamp}")
    Optional<HistoricStationCommodityMarketDatumEntity> findByStationIdAndCommodityIdAndTimestamp(@Param("stationId") UUID stationId, @Param("commodityId") UUID commodityId, @Param("timestamp") LocalDateTime timestamp);
    
    @ResultMap("HistoricStationCommodityMarketDatumResult")
    @Select("SELECT id, station_id, commodity_id, timestamp, mean_price, buy_price, stock, stock_bracket, sell_price, demand, demand_bracket, status_flags FROM historic_station_commodity_market_data WHERE id = #{historicStationCommodityMarketDatumId}")
    Optional<HistoricStationCommodityMarketDatumEntity> findById(@Param("historicStationCommodityMarketDatumId") UUID historicStationCommodityMarketDatumId);

    @ResultMap("HistoricStationCommodityMarketDatumResult")
    @Select("SELECT id, station_id, commodity_id, timestamp, mean_price, buy_price, stock, stock_bracket, sell_price, demand, demand_bracket, status_flags FROM historic_station_commodity_market_data WHERE commodity_id = #{commodityId} AND timestamp BETWEEN #{startTimestamp} AND #{endTimestamp}")
    List<HistoricStationCommodityMarketDatumEntity> findByCommodityIdAndTimestampBetween(@Param("commodityId") UUID commodityId, @Param("startTimestamp") LocalDateTime startTimestamp, @Param("endTimestamp") LocalDateTime endTimestamp);

    @Insert("INSERT INTO historic_station_commodity_market_data (id, station_id, commodity_id, timestamp, mean_price, buy_price, stock, stock_bracket, sell_price, demand, demand_bracket, status_flags) VALUES (#{id}, #{stationId}, #{commodityId}, #{timestamp}, #{meanPrice}, #{buyPrice}, #{stock}, #{stockBracket}, #{sellPrice}, #{demand}, #{demandBracket}, #{statusFlags})")
    int insert(HistoricStationCommodityMarketDatumEntity historicStationCommodityMarketDatumEntity);

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
    int deleteObsoleteForStationAndCommodity(@Param("stationId") UUID stationId, @Param("commodityId") UUID commodityId);
}
