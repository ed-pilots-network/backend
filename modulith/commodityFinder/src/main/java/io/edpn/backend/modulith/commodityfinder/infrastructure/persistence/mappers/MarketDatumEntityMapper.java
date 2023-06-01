package io.edpn.backend.modulith.commodityfinder.infrastructure.persistence.mappers;

import io.edpn.backend.modulith.commodityfinder.application.dto.persistence.CommodityEntity;
import io.edpn.backend.modulith.commodityfinder.application.dto.persistence.MarketDatumEntity;
import io.edpn.backend.modulith.mybatisutil.StringListTypeHandler;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface MarketDatumEntityMapper {

    @Select("SELECT * FROM market_datum WHERE station_id = #{stationId} AND commodity_id = #{commodityId}")
    @Results(id = "marketDatumResultMap", value = {
            @Result(property = "commodity", column = "id", javaType = CommodityEntity.class,
                    one = @One(select = "io.edpn.backend.modulith.commodityfinder.application.mapper.CommodityEntityMapper.findById")),
            @Result(property = "meanPrice", column = "mean_price", javaType = long.class),
            @Result(property = "buyPrice", column = "buy_price", javaType = long.class),
            @Result(property = "stock", column = "stock", javaType = long.class),
            @Result(property = "stockBracket", column = "stock_bracket", javaType = long.class),
            @Result(property = "sellPrice", column = "sell_price", javaType = long.class),
            @Result(property = "demand", column = "demand", javaType = long.class),
            @Result(property = "demandBracket", column = "demand_bracket", javaType = long.class),
            @Result(property = "statusFlags", column = "status_flags", javaType = List.class, typeHandler = StringListTypeHandler.class),
            @Result(property = "prohibited", column = "prohibited", javaType = boolean.class)
    })
    @ResultMap("marketDatumResultMap")
    Optional<MarketDatumEntity> findById(@Param("stationId") UUID stationId, @Param("commodityId") UUID commodityId);

    @Select("SELECT * FROM market_datum WHERE commodity_id = #{commodityId}")
    @ResultMap("marketDatumResultMap")
    List<MarketDatumEntity> findByStationId(UUID stationId);

    @Insert("INSERT INTO market_datum (station_id, commodity_id, mean_price, buy_price, stock, stock_bracket, sell_price, demand, demand_bracket, " +
            "status_flags, prohibited) VALUES (#{stationId}, #{commodityId}, #{meanPrice}, #{buyPrice}, #{stock}, #{stockBracket}, #{sellPrice}, " +
            "#{demand}, #{demandBracket}, #{statusFlags}, #{prohibited})")
    void insert(@Param("stationId") UUID stationId, MarketDatumEntity marketDatum);

    @Update("UPDATE market_datum SET mean_price = #{meanPrice}, buy_price = #{buyPrice}, stock = #{stock}, stock_bracket = #{stockBracket}, " +
            "sell_price = #{sellPrice}, demand = #{demand}, demand_bracket = #{demandBracket}, status_flags = #{statusFlags}, " +
            "prohibited = #{prohibited} WHERE station_id = #{stationId} AND commodity_id = #{commodityId}")
    void update(MarketDatumEntity marketDatum);

    @Delete("DELETE FROM market_datum WHERE station_id = #{stationId} AND commodity_id = #{commodityId}")
    void deleteById(@Param("stationId") UUID stationId, @Param("commodityId") UUID commodityId);

    @Delete("DELETE FROM market_datum WHERE station_id = #{stationId}")
    void deleteByStationId(@Param("stationId") UUID stationId);
}