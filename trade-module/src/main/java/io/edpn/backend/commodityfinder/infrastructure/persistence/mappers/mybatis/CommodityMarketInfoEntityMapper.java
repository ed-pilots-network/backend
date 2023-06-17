package io.edpn.backend.commodityfinder.infrastructure.persistence.mappers.mybatis;

import io.edpn.backend.commodityfinder.infrastructure.persistence.entity.CommodityMarketInfoEntity;
import io.edpn.backend.commodityfinder.infrastructure.persistence.entity.CommodityEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import io.edpn.backend.commodityfinder.infrastructure.persistence.entity.StationEntity;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

public interface CommodityMarketInfoEntityMapper {

    @Select({"SELECT *",
            "FROM  commodity_market_info_view",
            "WHERE commodity_id = #{commodityId}"})
    @Results(id = "commodityMarketInfoResultMap", value = {
            @Result(property = "commodity", column = "commodity_id", javaType = CommodityEntity.class,
                    one = @One(select = "io.edpn.backend.commodityfinder.infrastructure.persistence.mappers.mybatis.CommodityEntityMapper.findById")),
            @Result(column="max_buy_price", property="maxBuyPrice"),
            @Result(column="min_buy_price", property="minBuyPrice"),
            @Result(column="avg_buy_price", property="avgBuyPrice"),
            @Result(column="max_sell_price", property="maxSellPrice"),
            @Result(column="min_sell_price", property="minSellPrice"),
            @Result(column="avg_sell_price", property="avgSellPrice"),
            @Result(column="min_mean_price", property="minMeanPrice"),
            @Result(column="max_mean_price", property="maxMeanPrice"),
            @Result(column="average_mean_price", property="averageMeanPrice"),
            @Result(column="total_stock", property="totalStock"),
            @Result(column="total_demand", property="totalDemand"),
            @Result(column="total_stations", property="totalStations"),
            @Result(column="stations_with_buy_price", property="stationsWithBuyPrice"),
            @Result(column="stations_with_sell_price", property="stationsWithSellPrice"),
            @Result(column="stations_with_buy_price_lower_than_average", property="stationsWithBuyPriceLowerThanAverage"),
            @Result(column="stations_with_sell_price_higher_than_average", property="stationsWithSellPriceHigherThanAverage"),
            @Result(column="highest_selling_to_station", property="highestSellingToStation", javaType = StationEntity.class,
                    one = @One(select = "io.edpn.backend.commodityfinder.infrastructure.persistence.mappers.mybatis.StationEntityMapper.findById")),
            @Result(column="lowest_buying_from_station", property="lowestBuyingFromStation", javaType = StationEntity.class,
                    one = @One(select = "io.edpn.backend.commodityfinder.infrastructure.persistence.mappers.mybatis.StationEntityMapper.findById"))
    })
    Optional<CommodityMarketInfoEntity> findByCommodityId(@Param("commodityId") UUID commodityId);

    @Select({"SELECT *",
            "FROM commodity_market_info_view"})
    @ResultMap("commodityMarketInfoResultMap")
    List<CommodityMarketInfoEntity> findAll();

}
