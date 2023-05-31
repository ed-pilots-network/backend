package io.edpn.backend.modulith.commodityfinder.infrastructure.persistence.mappers;

import io.edpn.backend.modulith.commodityfinder.application.dto.persistence.MarketDatumEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface MarketDatumEntityMapper {

    @Select("SELECT * FROM market_datum WHERE id_station = #{stationId} AND id_commodity = #{commodityId}")
    Optional<MarketDatumEntity> findById(@Param("stationId") UUID stationId, @Param("commodityId") UUID commodityId);

    @Select("SELECT * FROM market_datum WHERE id_station = #{stationId} AND id_commodity = #{commodityId}")
    Collection<MarketDatumEntity> findByStationIds(@Param("stationId") UUID stationId);

    @Insert("INSERT INTO market_datum (id_station, id_commodity, mean_price, buy_price, stock, stock_bracket, sell_price, demand, demand_bracket, status_flags, prohibited) " +
            "VALUES (#{id.station.id}, #{id.commodityId.id}, #{meanPrice}, #{buyPrice}, #{stock}, #{stockBracket}, #{sellPrice}, #{demand}, #{demandBracket}, #{statusFlags}, #{prohibited})")
    void insert(MarketDatumEntity marketDatum);

    @Update("UPDATE market_datum SET mean_price = #{meanPrice}, buy_price = #{buyPrice}, stock = #{stock}, stock_bracket = #{stockBracket}, " +
            "sell_price = #{sellPrice}, demand = #{demand}, demand_bracket = #{demandBracket}, status_flags = #{statusFlags}, prohibited = #{prohibited} " +
            "WHERE id_station = #{id.station.id} AND id_commodity = #{id.commodityId.id}")
    void update(MarketDatumEntity marketDatum);

    @Delete("DELETE FROM market_datum WHERE id_station = #{stationId} AND id_commodity = #{commodityId}")
    void deleteById(@Param("stationId") UUID stationId, @Param("commodityId") UUID commodityId);

    @Delete("DELETE FROM market_datum WHERE id_station = #{stationId}")
    void deleteByStationId(@Param("stationId") UUID stationId);

}