package io.edpn.backend.trade.infrastructure.persistence.mappers.mybatis;

import io.edpn.backend.trade.infrastructure.persistence.entity.CommodityEntity;
import io.edpn.backend.trade.infrastructure.persistence.entity.MarketDatumEntity;
import io.edpn.backend.mybatisutil.StringListToArrayTypeHandler;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import static org.apache.ibatis.type.JdbcType.ARRAY;

public interface MarketDatumEntityMapper {

    @Select("SELECT * FROM market_datum WHERE station_id = #{stationId} AND commodity_id = #{commodityId} AND timestamp = #{timestamp}")
    @Results(id = "marketDatumResultMap", value = {
            @Result(property = "commodity", column = "commodity_id", javaType = CommodityEntity.class,
                    one = @One(select = "io.edpn.backend.trade.infrastructure.persistence.mappers.mybatis.CommodityEntityMapper.findById")),
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
    Optional<MarketDatumEntity> findById(@Param("stationId") UUID stationId, @Param("commodityId") UUID commodityId, @Param("timestamp") LocalDateTime timestamp);

    @Select({
            "SELECT EXISTS(",
            "SELECT 1",
            "FROM market_datum md",
            "left join station st on st.id = md.station_id",
            "left join system sy on sy.id = st.system_id",
            "WHERE st.name = #{stationName}",
            "AND sy.name =  #{systemName}",
            "AND timestamp =  #{timestamp}",
            "LIMIT 1)"
    })
    boolean existsByStationNameAndSystemNameAndTimestamp(@Param("systemName") String systemName, @Param("stationName") String stationName, @Param("timestamp") LocalDateTime timestamp);

    @Select("SELECT * FROM market_datum WHERE station_id = #{stationId}")
    @ResultMap("marketDatumResultMap")
    List<MarketDatumEntity> findByStationId(@Param("stationId") UUID stationId);

    @Insert("INSERT INTO market_datum (station_id, commodity_id, timestamp, mean_price, buy_price, stock, stock_bracket, sell_price, demand, demand_bracket, " +
            "status_flags, prohibited) VALUES (#{stationId}, #{marketDatum.commodity.id}, #{marketDatum.timestamp}, #{marketDatum.meanPrice}, #{marketDatum.buyPrice}, #{marketDatum.stock}, #{marketDatum.stockBracket}, #{marketDatum.sellPrice}, " +
            "#{marketDatum.demand}, #{marketDatum.demandBracket}, #{marketDatum.statusFlags, jdbcType=ARRAY, typeHandler=io.edpn.backend.mybatisutil.StringListToArrayTypeHandler}, #{marketDatum.prohibited})")
    void insert(@Param("stationId") UUID stationId, @Param("marketDatum") MarketDatumEntity marketDatum);

    @Update("UPDATE market_datum SET mean_price = #{meanPrice}, buy_price = #{buyPrice}, stock = #{stock}, stock_bracket = #{stockBracket}, " +
            "sell_price = #{sellPrice}, demand = #{demand}, demand_bracket = #{demandBracket}, status_flags = #{statusFlags, jdbcType=ARRAY, typeHandler=io.edpn.backend.mybatisutil.StringListToArrayTypeHandler}, " +
            "prohibited = #{prohibited} WHERE station_id = #{stationId} AND commodity_id = #{commodityId} AND timestamp = #{timestamp}")
    void update(MarketDatumEntity marketDatum);
    
    //TODO: Expand to full Request + Response information
    @Select("SELECT * FROM latest_market_data_view WHERE commodity_id=#{commodityId) ORDER_BY distance DESC")
    @ResultMap("marketDatumResultMap")
    List<MarketDatumEntity> findAllOrderByDistance(@Param("commodityId") UUID commodityId);
    
//    @Select("SELECT * FROM market_datum WHERE station_id = #{stationId} AND commodity_id = #{commodityId} AND timestamp = #{timestamp}")
//    @ResultMap("marketDatumResultMap")
//    List<MarketDatumEntity> findAllOrderByPrice(MarketDatumEntity exampleOf);
}
