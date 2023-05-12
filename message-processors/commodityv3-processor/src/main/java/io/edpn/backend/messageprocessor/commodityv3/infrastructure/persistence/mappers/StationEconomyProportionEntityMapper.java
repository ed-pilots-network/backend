package io.edpn.backend.messageprocessor.commodityv3.infrastructure.persistence.mappers;

import io.edpn.backend.messageprocessor.commodityv3.application.dto.persistence.StationEconomyProportionEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.UUID;

@Mapper
public interface StationEconomyProportionEntityMapper {

    @Insert("INSERT INTO station_economy_proportions (station_id, economy_id, proportion) VALUES (#{stationId}, #{economyId}, #{proportion})")
    int insert(StationEconomyProportionEntity entity);

    @Delete("DELETE FROM station_economy_proportions WHERE station_id = #{stationId}")
    int delete(@Param("stationId") UUID stationId);
}
