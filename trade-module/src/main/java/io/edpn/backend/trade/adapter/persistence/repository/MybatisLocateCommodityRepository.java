package io.edpn.backend.trade.adapter.persistence.repository;

import io.edpn.backend.trade.adapter.persistence.entity.MybatisLocateCommodityEntity;
import io.edpn.backend.trade.adapter.persistence.entity.MybatisStationEntity;
import io.edpn.backend.trade.adapter.persistence.entity.MybatisValidatedCommodityEntity;
import io.edpn.backend.trade.adapter.persistence.filter.MybatisLocateCommodityFilter;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

public interface MybatisLocateCommodityRepository {
    
    //TODO: update order by distance function for postgis
    @Select("""
            <script>
            	with filtered_lcv as (SELECT *
               						  FROM locate_commodity_view lcv
                					  <if test='commodityDisplayName != null'>
                                      WHERE lcv.commodity_id = (SELECT vcv.id FROM validated_commodity_view vcv WHERE vcv.display_name = #{commodityDisplayName})
            						  </if>
            						 ),
            		 cte AS (SELECT timestamp,
            						commodity_id,
            						station_id,
            						system_id,
            						stock,
            						demand,
            						buy_price,
            						sell_price,
            						x_coordinate,
            						y_coordinate,
            						z_coordinate,
            						planetary,
            						require_odyssey,
            						fleet_carrier
            				 FROM filtered_lcv
            				 WHERE 1 = 1
            					<if test='!(includePlanetary == null || includePlanetary)'>AND planetary = false </if>
            					<if test='!(includeOdyssey == null || includeOdyssey)'>AND require_odyssey = false </if>
            					<if test='!(includeFleetCarriers == null || includeFleetCarriers)'>AND fleet_carrier = false </if>
            					<if test='minSupply != null'>AND stock >= #{minSupply} </if>
            					<if test='minDemand != null'>AND demand >= #{minDemand} </if>
            					<choose>
            						<when test='shipSize == "LARGE"'>
            							AND max_landing_pad_size = 'LARGE'
            						</when>
            						<when test='shipSize == "MEDIUM"'>
            							AND max_landing_pad_size IN ('MEDIUM', 'LARGE')\s
            						</when>
            						<when test='shipSize == "SMALL"'>
            							AND max_landing_pad_size IN ('MEDIUM', 'LARGE', 'SMALL', 'UNKNOWN')
            						</when>
            					</choose>
            					)
            	SELECT cte.timestamp,
            		   cte.commodity_id,
            		   cte.station_id,
            		   cte.system_id,
            		   cte.stock,
            		   cte.demand,
            		   cte.buy_price,
            		   cte.sell_price,
            		   <choose>
            			<when test='xCoordinate == null || yCoordinate == null || zCoordinate == null'>
            				sqrt(pow(cte.x_coordinate, 2) +
            				pow(cte.y_coordinate, 2) +
            				pow(z_coordinate, 2)) as distance,
            			</when>
            			<otherwise>
            				sqrt(pow(#{xCoordinate} - cte.x_coordinate, 2) +
            				pow(#{yCoordinate} - cte.y_coordinate, 2) +
            				pow(#{zCoordinate} - z_coordinate, 2)) as distance,
            			</otherwise>
            		   </choose>
            		   cte.planetary,
            		   cte.require_odyssey,
                		   cte.fleet_carrier,
            		   <if test='page != null'>
                           #{page.size} as page_size,
                           #{page.page} as page_number,
                           (SELECT count(1) FROM cte) as total_items
            		   </if>
            		   <if test='page == null'>
                           null as page_size,
                           null as current_page,
                           null as total_items
            		   </if>
            	FROM cte
            	ORDER BY distance
            	<if test='page != null'>
            		LIMIT #{page.size}
            		OFFSET (#{page.size} * #{page.page})
            	</if>
            </script>
            """
    )
    @Results(id = "findCommodityResultMap", value = {
            @Result(property = "priceUpdatedAt", column = "timestamp", javaType = LocalDateTime.class),
            @Result(property = "validatedCommodity", column = "commodity_id", javaType = MybatisValidatedCommodityEntity.class,
                    one = @One(select = "io.edpn.backend.trade.adapter.persistence.repository.MybatisValidatedCommodityRepository.findById")),
            @Result(property = "station", column = "station_id", javaType = MybatisStationEntity.class,
                    one = @One(select = "io.edpn.backend.trade.adapter.persistence.repository.MybatisStationRepository.findById")),
            @Result(property = "supply", column = "stock", javaType = Long.class),
            @Result(property = "demand", column = "demand", javaType = Long.class),
            @Result(property = "buyPrice", column = "buy_price", javaType = Long.class),
            @Result(property = "sellPrice", column = "sell_price", javaType = Long.class),
            @Result(property = "distance", column = "distance", javaType = Double.class),
            @Result(property = "pageSize", column = "pageSize", javaType = Integer.class),
            @Result(property = "currentPage", column = "current_page", javaType = Integer.class),
            @Result(property = "totalItems", column = "total_items", javaType = Integer.class)

    })
    List<MybatisLocateCommodityEntity> locateCommodityByFilter(MybatisLocateCommodityFilter locateCommodityFilterPersistence);
    
}
