package io.edpn.backend.trade.adapter.persistence.repository;

import io.edpn.backend.mybatisutil.StringListToArrayTypeHandler;
import io.edpn.backend.trade.adapter.persistence.entity.MybatisCommodityEntity;
import io.edpn.backend.trade.adapter.persistence.entity.MybatisMarketDatumEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.apache.ibatis.type.JdbcType.ARRAY;

public interface MybatisLatestMarketDatumRepository {

    @Select("SELECT * FROM latest_market_datum WHERE station_id = #{stationId} AND commodity_id = #{commodityId} AND timestamp = #{timestamp}")
    @Results(id = "marketDatumResultMap", value = {
            @Result(property = "commodity", column = "commodity_id", javaType = MybatisCommodityEntity.class,
                    one = @One(select = "io.edpn.backend.trade.adapter.persistence.repository.MybatisCommodityRepository.findById")),
            @Result(property = "timestamp", column = "timestamp", javaType = LocalDateTime.class),
            @Result(property = "meanPrice", column = "mean_price", javaType = long.class),
            @Result(property = "buyPrice", column = "buy_price", javaType = long.class),
            @Result(property = "stock", column = "stock", javaType = long.class),
            @Result(property = "stockBracket", column = "stock_bracket", javaType = long.class),
            @Result(property = "sellPrice", column = "sell_price", javaType = long.class),
            @Result(property = "demand", column = "demand", javaType = long.class),
            @Result(property = "demandBracket", column = "demand_bracket", javaType = long.class),
            @Result(property = "statusFlags", column = "status_flags", javaType = List.class, jdbcType = ARRAY, typeHandler = StringListToArrayTypeHandler.class),
            @Result(property = "prohibited", column = "prohibited", javaType = boolean.class)
    })
    Optional<MybatisMarketDatumEntity> findById(@Param("stationId") UUID stationId, @Param("commodityId") UUID commodityId);

    @Insert({
            "INSERT INTO latest_market_datum (station_id, commodity_id, mean_price, buy_price, stock, stock_bracket, sell_price, demand, demand_bracket, status_flags, prohibited, timestamp)",
            "VALUES (#{stationId}, #{marketDatum.commodity.id}, #{marketDatum.meanPrice}, #{marketDatum.buyPrice}, #{marketDatum.stock}, #{marketDatum.stockBracket}, #{marketDatum.sellPrice}, #{marketDatum.demand}, #{marketDatum.demandBracket}, #{marketDatum.statusFlags, jdbcType=ARRAY, typeHandler=io.edpn.backend.mybatisutil.StringListToArrayTypeHandler}, #{marketDatum.prohibited}, #{marketDatum.timestamp})",
            "ON CONFLICT (station_id, commodity_id)",
            "DO UPDATE ",
            "SET mean_price = EXCLUDED.mean_price,",
            "buy_price = EXCLUDED.buy_price,",
            "stock = EXCLUDED.stock,",
            "stock_bracket = EXCLUDED.stock_bracket,",
            "sell_price = EXCLUDED.sell_price,",
            "demand = EXCLUDED.demand,",
            "demand_bracket = EXCLUDED.demand_bracket,",
            "status_flags = EXCLUDED.status_flags,",
            "prohibited = EXCLUDED.prohibited,",
            "timestamp = EXCLUDED.timestamp",
            "WHERE EXCLUDED.timestamp > latest_market_datum.timestamp"
    })
    void createOrUpdateExistingWhenNewer(@Param("stationId") UUID stationId, @Param("marketDatum") MybatisMarketDatumEntity marketDatum);
}
