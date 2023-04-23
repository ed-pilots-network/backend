package io.edpn.edpnbackend.infrastructure.persistence.mappers;

import io.edpn.edpnbackend.application.dto.persistence.CommodityMarketDatumEntity;
import io.edpn.edpnbackend.infrastructure.persistence.util.UuidTypeHandler;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface CommodityMarketDatumEntityMapper {

    @Results(id = "CommodityMarketDatumResult", value = {
            @Result(property = "id", column = "id", javaType = UUID.class, typeHandler = UuidTypeHandler.class),
            @Result(property = "meanPrice", column = "mean_price"),
            @Result(property = "buyPrice", column = "buy_price"),
            @Result(property = "stock", column = "stock"),
            @Result(property = "stockBracket", column = "stock_bracket"),
            @Result(property = "sellPrice", column = "sell_price"),
            @Result(property = "demand", column = "demand"),
            @Result(property = "demandBracket", column = "demand_bracket"),
            @Result(property = "statusFlags", column = "status_flags")
    })
    @Select("SELECT id, mean_price, buy_price, stock, stock_bracket, sell_price, demand, demand_bracket, status_flags FROM commodity_market_data")
    List<CommodityMarketDatumEntity> findAll();

    @ResultMap("CommodityMarketDatumResult")
    @Select("SELECT id, mean_price, buy_price, stock, stock_bracket, sell_price, demand, demand_bracket, status_flags FROM commodity_market_data WHERE id = #{id}")
    Optional<CommodityMarketDatumEntity> findById(@Param("id") UUID id);

    @Insert("INSERT INTO commodity_market_data (id, mean_price, buy_price, stock, stock_bracket, sell_price, demand, demand_bracket, status_flags) VALUES (#{id}, #{meanPrice}, #{buyPrice}, #{stock}, #{stockBracket}, #{sellPrice}, #{demand}, #{demandBracket}, #{statusFlags})")
    int insert(CommodityMarketDatumEntity commodityMarketDatumEntity);

    @Update("UPDATE commodity_market_data SET mean_price = #{meanPrice}, buy_price = #{buyPrice}, stock = #{stock}, stock_bracket = #{stockBracket}, sell_price = #{sellPrice}, demand = #{demand}, demand_bracket = #{demandBracket}, status_flags = #{statusFlags} WHERE id = #{id}")
    int update(CommodityMarketDatumEntity commodityMarketDatumEntity);

    @Delete("DELETE FROM historic_station_commodities WHERE id = #{id}")
    int deleteById(@Param("id") UUID id);
}
