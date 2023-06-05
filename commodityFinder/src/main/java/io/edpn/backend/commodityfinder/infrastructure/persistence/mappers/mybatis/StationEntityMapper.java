package io.edpn.backend.commodityfinder.infrastructure.persistence.mappers.mybatis;

import io.edpn.backend.commodityfinder.infrastructure.persistence.entity.StationEntity;
import io.edpn.backend.commodityfinder.infrastructure.persistence.entity.SystemEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface StationEntityMapper {

    @Select("SELECT * FROM station WHERE id = #{id}")
    @Results(id = "stationResultMap", value = {
            @Result(property = "id", column = "id", javaType = UUID.class),
            @Result(property = "marketId", column = "market_id", javaType = Long.class),
            @Result(property = "name", column = "name", javaType = String.class),
            @Result(property = "arrivalDistance", column = "arrival_distance", javaType = Double.class),
            @Result(property = "system", column = "system_id", javaType = SystemEntity.class,
                    one = @One(select = "io.edpn.backend.modulith.commodityfinder.application.mapper.SystemEntityMapper.findById")),
            @Result(property = "planetary", column = "planetary", javaType = boolean.class),
            @Result(property = "requireOdyssey", column = "require_odyssey", javaType = boolean.class),
            @Result(property = "fleetCarrier", column = "fleet_carrier", javaType = boolean.class),
            @Result(property = "maxLandingPadSize", column = "max_landing_pad_size", javaType = String.class),
            @Result(property = "marketUpdatedAt", column = "market_updated_at", javaType = LocalDateTime.class),
            @Result(property = "marketData", column = "id", javaType = List.class,
                    many = @Many(select = "io.edpn.backend.modulith.commodityfinder.application.mapper.MarketDatumEntityMapper.findByStationId"))
    })
    @ResultMap("stationResultMap")
    Optional<StationEntity> findById(UUID id);

    @ResultMap("stationResultMap")
    Optional<StationEntity> findBySystemIdAndStationName(UUID systemId, String stationName);

    @Insert("INSERT INTO station (id, market_id, name, arrival_distance, system_id, planetary, require_odyssey, fleet_carrier, max_landing_pad_size, market_updated_at) " +
            "VALUES (#{id}, #{marketId}, #{name}, #{systemId}, #{planetary}, #{requireOdyssey}, #{fleetCarrier}, #{maxLandingPadSize}, #{marketUpdatedAt})")
    void insert(StationEntity station);

    @Update("UPDATE station SET market_id = #{marketId}, name = #{name}, arrival_distance = #{arrivalDistance}, system_id = #{systemId}, planetary = #{planetary}, " +
            "require_odyssey = #{requireOdyssey}, fleet_carrier = #{fleetCarrier}, max_landing_pad_size = #{maxLandingPadSize}, " +
            "market_updated_at = #{marketUpdatedAt} WHERE id = #{id}")
    void update(StationEntity station);

    @Delete("DELETE FROM station WHERE id = #{id}")
    void delete(UUID id);
}