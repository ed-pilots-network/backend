package io.edpn.backend.trade.infrastructure.persistence.mappers.mybatis;

import io.edpn.backend.trade.infrastructure.persistence.entity.LocateCommodityEntity;
import io.edpn.backend.trade.infrastructure.persistence.entity.ValidatedCommodityEntity;
import io.edpn.backend.trade.infrastructure.persistence.filter.LocateCommodityFilterPersistence;
import io.edpn.backend.trade.infrastructure.persistence.entity.StationEntity;
import io.edpn.backend.trade.infrastructure.persistence.entity.SystemEntity;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

public interface LocateCommodityEntityMapper {
    
    //TODO: update order by distance function for postgis
    @Select("""
            <script>
            SELECT
            timestamp, commodity_id, station_id, system_id, stock, demand, buy_price, sell_price,
            sqrt(pow(#{xCoordinate} - x_coordinate, 2) + pow(#{yCoordinate} - y_coordinate, 2) + pow(#{zCoordinate} - z_coordinate, 2)) as distance,
            planetary,
            require_odyssey,
            fleet_carrier
            FROM locate_commodity_view
            INNER JOIN validated_commodity_view vcv
            ON commodity_id = vcv.id
            WHERE
            vcv.display_name =#{commodityDisplayName}
            <if test='!includePlanetary'>AND planetary = false </if>
            <if test='!includeOdyssey'>AND require_odyssey = false </if>
            <if test='!includeFleetCarriers'>AND fleet_carrier = false </if>
            AND stock >= #{minSupply}
            AND demand >= #{minDemand}
            <if test='maxLandingPadSize == "LARGE"'>AND max_landing_pad_size = 'LARGE'</if>
            <if test='maxLandingPadSize == "MEDIUM"'>AND max_landing_pad_size IN ('MEDIUM', 'LARGE')</if>
            ORDER BY distance
            </script>"""
    )
    @Results(id = "findCommodityResultMap", value = {
            @Result(property = "pricesUpdatedAt", column = "timestamp", javaType = LocalDateTime.class),
            @Result(property = "validatedCommodity", column = "commodity_id", javaType = ValidatedCommodityEntity.class,
                    one = @One(select = "io.edpn.backend.trade.infrastructure.persistence.mappers.mybatis.ValidatedCommodityEntityMapper.findById")),
            @Result(property = "station", column = "station_id", javaType = StationEntity.class,
                    one = @One(select = "io.edpn.backend.trade.infrastructure.persistence.mappers.mybatis.StationEntityMapper.findById")),
            @Result(property = "system", column = "system_id", javaType = SystemEntity.class,
                    one = @One(select = "io.edpn.backend.trade.infrastructure.persistence.mappers.mybatis.SystemEntityMapper.findById")),
            @Result(property = "supply", column = "stock", javaType = Long.class),
            @Result(property = "demand", column = "demand", javaType = Long.class),
            @Result(property = "buyPrice", column = "buy_price", javaType = Long.class),
            @Result(property = "sellPrice", column = "sell_price", javaType = Long.class),
            @Result(property = "distance", column = "distance", javaType = Double.class)

    })
    List<LocateCommodityEntity> locateCommodityByFilter(LocateCommodityFilterPersistence locateCommodityFilterPersistence);
    
}
