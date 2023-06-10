package io.edpn.backend.commodityfinder.infrastructure.persistence.mappers.mybatis;

import io.edpn.backend.commodityfinder.infrastructure.persistence.entity.CommodityMarketInfoEntity;
import io.edpn.backend.commodityfinder.infrastructure.persistence.entity.CommodityEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import io.edpn.backend.commodityfinder.infrastructure.persistence.entity.StationEntity;
import org.apache.ibatis.annotations.Many;
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
            @Result(column="maxBuyPrice", property="maxBuyPrice"),
            @Result(column="minBuyPrice", property="minBuyPrice"),
            @Result(column="avgBuyPrice", property="avgBuyPrice"),
            @Result(column="maxSellPrice", property="maxSellPrice"),
            @Result(column="minSellPrice", property="minSellPrice"),
            @Result(column="avgSellPrice", property="avgSellPrice"),
            @Result(column="minMeanPrice", property="minMeanPrice"),
            @Result(column="maxMeanPrice", property="maxMeanPrice"),
            @Result(column="averageMeanPrice", property="averageMeanPrice"),
            @Result(column="totalStock", property="totalStock"),
            @Result(column="totalDemand", property="totalDemand"),
            @Result(column="totalStations", property="totalStations"),
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
