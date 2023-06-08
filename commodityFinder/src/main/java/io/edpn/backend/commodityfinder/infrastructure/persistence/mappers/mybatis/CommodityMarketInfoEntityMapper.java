package io.edpn.backend.commodityfinder.infrastructure.persistence.mappers.mybatis;

import io.edpn.backend.commodityfinder.infrastructure.persistence.entity.CommodityMarketInfoEntity;
import io.edpn.backend.commodityfinder.infrastructure.persistence.entity.CommodityEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
    Optional<CommodityMarketInfoEntity> findByCommodityId(@Param("commodityId") UUID commodityId);

    @Select({"SELECT *",
            "FROM  commodity_market_info_view"})
    @ResultMap("commodityInfoResultMap")
    List<CommodityMarketInfoEntity> findAll();

}
