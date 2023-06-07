package io.edpn.backend.commodityfinder.infrastructure.persistence.mappers.mybatis;

import io.edpn.backend.commodityfinder.infrastructure.persistence.entity.BestCommodityPriceEntity;
import io.edpn.backend.commodityfinder.infrastructure.persistence.entity.CommodityEntity;
import io.edpn.backend.commodityfinder.infrastructure.persistence.entity.MarketDatumEntity;
import io.edpn.backend.mybatisutil.StringListTypeHandler;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
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

public interface MarketDatumEntityMapper {

    @Select("SELECT * FROM market_datum WHERE station_id = #{stationId} AND commodity_id = #{commodityId}")
    @Results(id = "marketDatumResultMap", value = {
            @Result(property = "commodity", column = "commodity_id", javaType = CommodityEntity.class,
                    one = @One(select = "io.edpn.backend.commodityfinder.infrastructure.persistence.mappers.mybatis.CommodityEntityMapper.findById")),
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
    Optional<MarketDatumEntity> findById(@Param("stationId") UUID stationId, @Param("commodityId") UUID commodityId);

    @Select("SELECT * FROM market_datum WHERE station_id = #{stationId}")
    @ResultMap("marketDatumResultMap")
    List<MarketDatumEntity> findByStationId(@Param("stationId") UUID stationId);

    @Insert("INSERT INTO market_datum (station_id, commodity_id, mean_price, buy_price, stock, stock_bracket, sell_price, demand, demand_bracket, " +
            "status_flags, prohibited) VALUES (#{stationId}, #{marketDatum.commodity.id}, #{marketDatum.meanPrice}, #{marketDatum.buyPrice}, #{marketDatum.stock}, #{marketDatum.stockBracket}, #{marketDatum.sellPrice}, " +
            "#{marketDatum.demand}, #{marketDatum.demandBracket}, #{marketDatum.statusFlags}, #{marketDatum.prohibited})")
    void insert(@Param("stationId") UUID stationId, @Param("marketDatum") MarketDatumEntity marketDatum);

    @Update("UPDATE market_datum SET mean_price = #{meanPrice}, buy_price = #{buyPrice}, stock = #{stock}, stock_bracket = #{stockBracket}, " +
            "sell_price = #{sellPrice}, demand = #{demand}, demand_bracket = #{demandBracket}, status_flags = #{statusFlags}, " +
            "prohibited = #{prohibited} WHERE station_id = #{stationId} AND commodity_id = #{commodityId}")
    void update(MarketDatumEntity marketDatum);

    @Delete("DELETE FROM market_datum WHERE station_id = #{stationId} AND commodity_id = #{commodityId}")
    void deleteById(@Param("stationId") UUID stationId, @Param("commodityId") UUID commodityId);

    @Delete("DELETE FROM market_datum WHERE station_id = #{stationId}")
    void deleteByStationId(@Param("stationId") UUID stationId);

    @Select("SELECT commodity_id, MAX(buy_price) AS maxBuyPrice, MIN(sell_price) AS minSellPrice, AVG(mean_price) AS averagePrice, " +
            "COUNT(DISTINCT station_id) AS totalStations, " +
            "COUNT(DISTINCT CASE WHEN buy_price IS NOT NULL THEN station_id END) * 100.0 / COUNT(DISTINCT station_id) AS percentStationsWithBuyPrice, " +
            "COUNT(DISTINCT CASE WHEN buy_price > AVG(mean_price) THEN station_id END) * 100.0 / COUNT(DISTINCT station_id) AS percentStationsWithBuyPriceAboveAverage, " +
            "COUNT(DISTINCT CASE WHEN sell_price IS NOT NULL THEN station_id END) * 100.0 / COUNT(DISTINCT station_id) AS percentStationsWithSellPrice, " +
            "COUNT(DISTINCT CASE WHEN sell_price < AVG(mean_price) THEN station_id END) * 100.0 / COUNT(DISTINCT station_id) AS percentStationsWithSellPriceBelowAverage, " +
            "GROUP_CONCAT(DISTINCT CASE WHEN sell_price = (SELECT MIN(sell_price) FROM market_datum WHERE commodity_id = #{commodityId}) THEN station_id END) AS stationsWithLowestSellPrice, " +
            "GROUP_CONCAT(DISTINCT CASE WHEN buy_price = (SELECT MAX(buy_price) FROM market_datum WHERE commodity_id = #{commodityId}) THEN station_id END) AS stationsWithHighestBuyPrice " +
            "FROM market_datum WHERE commodity_id = #{commodityId} " +
            "GROUP BY commodity_id")
    @Results(id = "commodityInfoResultMap", value = {
            @Result(property = "commodity", column = "commodity_id", javaType = CommodityEntity.class,
                    one = @One(select = "io.edpn.backend.commodityfinder.infrastructure.persistence.mappers.mybatis.CommodityEntityMapper.findById")),
            @Result(property = "maxBuyPrice", column = "maxBuyPrice", javaType = long.class),
            @Result(property = "minSellPrice", column = "minSellPrice", javaType = long.class),
            @Result(property = "averagePrice", column = "averagePrice", javaType = double.class),
            @Result(property = "percentStationsWithBuyPrice", column = "percentStationsWithBuyPrice", javaType = double.class),
            @Result(property = "percentStationsWithBuyPriceAboveAverage", column = "percentStationsWithBuyPriceAboveAverage", javaType = double.class),
            @Result(property = "percentStationsWithSellPrice", column = "percentStationsWithSellPrice", javaType = double.class),
            @Result(property = "percentStationsWithSellPriceBelowAverage", column = "percentStationsWithSellPriceBelowAverage", javaType = double.class),
            @Result(property = "stationsWithLowestSellPrice", column = "stationsWithLowestSellPrice", javaType = String.class),
            @Result(property = "stationsWithHighestBuyPrice", column = "stationsWithHighestBuyPrice", javaType = String.class),
            @Result(property = "stationEntitiesWithLowestSellPrice", column = "stationsWithLowestSellPrice", javaType = List.class,
                    many = @Many(select = "io.edpn.backend.commodityfinder.infrastructure.persistence.mappers.mybatis.StationEntityMapper.findById")),
            @Result(property = "stationEntitiesWithHighestBuyPrice", column = "stationsWithHighestBuyPrice", javaType = List.class,
                    many = @Many(select = "io.edpn.backend.commodityfinder.infrastructure.persistence.mappers.mybatis.StationEntityMapper.findById"))
    })
    Optional<BestCommodityPriceEntity> getBestCommodityPrice(@Param("commodityId") UUID commodityId);

}