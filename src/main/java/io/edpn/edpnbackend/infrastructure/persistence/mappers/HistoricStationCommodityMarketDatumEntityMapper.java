package io.edpn.edpnbackend.infrastructure.persistence.mappers;

import io.edpn.edpnbackend.application.dto.persistence.HistoricStationCommodityMarketDatumEntity;
import io.edpn.edpnbackend.infrastructure.persistence.util.UuidTypeHandler;
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
            @Result(property = "marketDatumId", column = "market_datum_id", javaType = UUID.class, typeHandler = UuidTypeHandler.class)
    })
    @Select("SELECT id, station_id, commodity_id, timestamp, market_datum_id FROM historic_station_commodity_market_data")
    List<HistoricStationCommodityMarketDatumEntity> findAll();

    @ResultMap("HistoricStationCommodityMarketDatumResult")
    @Select("SELECT id, station_id, commodity_id, timestamp, market_datum_id FROM historic_station_commodity_market_data WHERE station_id = #{stationId} AND commodity_id = #{commodityId} AND timestamp = #{timestamp}")
    Optional<HistoricStationCommodityMarketDatumEntity> findById(@Param("stationId") UUID stationId, @Param("commodityId") UUID commodityId, @Param("timestamp") LocalDateTime timestamp);

    @ResultMap("HistoricStationCommodityMarketDatumResult")
    @Select("SELECT id, station_id, commodity_id, timestamp, market_datum_id FROM historic_station_commodity_market_data WHERE commodity_id = #{commodityId} AND timestamp BETWEEN #{startTimestamp} AND #{endTimestamp}")
    List<HistoricStationCommodityMarketDatumEntity> findByIdCommodityIdAndTimestampBetween(@Param("commodityId") UUID commodityId, @Param("startTimestamp") LocalDateTime startTimestamp, @Param("endTimestamp") LocalDateTime endTimestamp);

    @ResultMap("HistoricStationCommodityMarketDatumResult")
    @Select("SELECT id, station_id, commodity_id, timestamp, market_datum_id FROM historic_station_commodity_market_data WHERE station_id = #{stationId} AND timestamp BETWEEN #{startTimestamp} AND #{endTimestamp}")
    List<HistoricStationCommodityMarketDatumEntity> findByIdStationIdAndTimestampBetween(@Param("stationId") UUID stationId, @Param("startTimestamp") LocalDateTime startTimestamp, @Param("endTimestamp") LocalDateTime endTimestamp);

    @ResultMap("HistoricStationCommodityMarketDatumResult")
    @Select("SELECT id, station_id, commodity_id, timestamp, market_datum_id FROM historic_station_commodity_market_data WHERE commodity_id = #{commodityId} AND timestamp = (SELECT MAX(timestamp) FROM historic_station_commodity_market_data WHERE commodity_id = #{commodityId})")
    List<HistoricStationCommodityMarketDatumEntity> findLatestByIdCommodityId(@Param("commodityId") UUID commodityId);

    @ResultMap("HistoricStationCommodityMarketDatumResult")
    @Select("SELECT id, station_id, commodity_id, timestamp, market_datum_id FROM historic_station_commodity_market_data WHERE station_id = #{stationId} AND timestamp = (SELECT MAX(timestamp) FROM historic_station_commodity_market_data WHERE station_id = #{stationId})")
    List<HistoricStationCommodityMarketDatumEntity> findLatestByIdStationId(@Param("stationId") UUID stationId);

    @Insert("INSERT INTO historic_station_commodity_market_data (id, station_id, commodity_id, timestamp, market_datum_id) VALUES (#{id}, #{stationId}, #{commodityId}, #{timestamp}, #{marketDatumId})")
    int insert(HistoricStationCommodityMarketDatumEntity historicStationCommodityMarketDatumEntity);

    @Update("UPDATE historic_station_commodity_market_data SET station_id = #{stationId}, commodity_id = #{commodityId}, timestamp = #{timestamp}, market_datum_id = #{marketDatumId} WHERE id = #{id} ")
    int update(HistoricStationCommodityMarketDatumEntity historicStationCommodityMarketDatumEntity);

    @Delete("DELETE FROM historic_station_commodity_market_data WHERE id = #{id}")
    int deleteById(@Param("id") UUID id);
}
