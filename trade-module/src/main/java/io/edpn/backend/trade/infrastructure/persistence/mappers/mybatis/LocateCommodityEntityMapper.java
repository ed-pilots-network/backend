package io.edpn.backend.trade.infrastructure.persistence.mappers.mybatis;

import io.edpn.backend.trade.infrastructure.persistence.entity.LocateCommodityEntity;
import io.edpn.backend.trade.infrastructure.persistence.filter.LocateCommodityFilterPersistence;
import io.edpn.backend.trade.infrastructure.persistence.entity.CommodityEntity;
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
            SELECT timestamp, commodity_id, station_id, system_id, stock, demand FROM locate_commodity_view
            WHERE
            commodity_id=#{commodityId}
            <if test='!includePlanetary'>AND planetary = false </if>
            <if test='!includeOdyssey'>AND require_odyssey = false </if>
            <if test='!includeFleetCarriers'>AND fleet_carrier = false </if>
            AND stock >= #{minSupply}
            AND demand >= #{minDemand}
            <if test='maxLandingPadSize == "SMALL"'>AND max_landing_pad_size = 'SMALL'</if>
            <if test='maxLandingPadSize == "MEDIUM"'>AND max_landing_pad_size IN ('SMALL', 'MEDIUM')</if>
            ORDER BY sqrt(pow(#{xCoordinate} - x_coordinate, 2) + pow(#{yCoordinate} - y_coordinate, 2) + pow(#{zCoordinate} - z_coordinate, 2))
            </script>"""
    )
    @Results(id = "findCommodityResultMap", value = {
            @Result(property = "pricesUpdatedAt", column = "timestamp", javaType = LocalDateTime.class),
            @Result(property = "commodity", column = "commodity_id", javaType = CommodityEntity.class,
                    one = @One(select = "io.edpn.backend.trade.infrastructure.persistence.mappers.mybatis.CommodityEntityMapper.findById")),
            @Result(property = "station", column = "station_id", javaType = StationEntity.class,
                    one = @One(select = "io.edpn.backend.trade.infrastructure.persistence.mappers.mybatis.StationEntityMapper.findById")),
            @Result(property = "system", column = "system_id", javaType = SystemEntity.class,
                    one = @One(select = "io.edpn.backend.trade.infrastructure.persistence.mappers.mybatis.SystemEntityMapper.findById")),
            @Result(property = "supply", column = "stock", javaType = Long.class),
            @Result(property = "demand", column = "demand", javaType = Long.class)
    })
    List<LocateCommodityEntity> locateCommodityByFilter(LocateCommodityFilterPersistence locateCommodityFilterPersistence);
    
}
