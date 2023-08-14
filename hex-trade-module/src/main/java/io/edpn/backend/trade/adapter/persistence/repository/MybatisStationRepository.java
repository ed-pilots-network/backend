package io.edpn.backend.trade.adapter.persistence.repository;

import io.edpn.backend.mybatisutil.UuidTypeHandler;
import io.edpn.backend.trade.adapter.persistence.entity.MybatisStationEntity;
import io.edpn.backend.trade.adapter.persistence.entity.MybatisSystemEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MybatisStationRepository {

    @Select("SELECT * FROM station WHERE id = #{id}")
    @Results(id = "stationResultMap", value = {
            @Result(property = "id", column = "id", javaType = UUID.class, typeHandler = UuidTypeHandler.class),
            @Result(property = "marketId", column = "market_id", javaType = Long.class),
            @Result(property = "name", column = "name", javaType = String.class),
            @Result(property = "arrivalDistance", column = "arrival_distance", javaType = Double.class),
            @Result(property = "system", column = "system_id", javaType = MybatisSystemEntity.class,
                    one = @One(select = "io.edpn.backend.trade.adapter.persistence.repository.MybatisSystemRepository.findById")),
            @Result(property = "planetary", column = "planetary", javaType = boolean.class),
            @Result(property = "requireOdyssey", column = "require_odyssey", javaType = boolean.class),
            @Result(property = "fleetCarrier", column = "fleet_carrier", javaType = boolean.class),
            @Result(property = "maxLandingPadSize", column = "max_landing_pad_size", javaType = String.class),
            @Result(property = "marketUpdatedAt", column = "market_updated_at", javaType = LocalDateTime.class),
            @Result(property = "marketData", column = "id", javaType = List.class,
                    many = @Many(select = "io.edpn.backend.trade.adapter.persistence.repository.MybatisMarketDatumRepository.findByStationId"))
    })
    Optional<MybatisStationEntity> findById(@Param("id") UUID id);

    @ResultMap("stationResultMap")
    @Select("SELECT * FROM station WHERE system_id = #{systemId} and name = #{stationName}")
    Optional<MybatisStationEntity> findBySystemIdAndStationName(@Param("systemId") UUID systemId, @Param("stationName") String stationName);

    @Insert("INSERT INTO station (id, market_id, name, arrival_distance, system_id, planetary, require_odyssey, fleet_carrier, max_landing_pad_size, market_updated_at) " +
            "VALUES (#{id}, #{marketId}, #{name}, #{arrivalDistance}, #{system.id}, #{planetary}, #{requireOdyssey}, #{fleetCarrier}, #{maxLandingPadSize}, #{marketUpdatedAt})")
    void insert(MybatisStationEntity station);

    @Update("UPDATE station SET market_id = #{marketId}, name = #{name}, arrival_distance = #{arrivalDistance}, system_id = #{system.id}, planetary = #{planetary}, " +
            "require_odyssey = #{requireOdyssey}, fleet_carrier = #{fleetCarrier}, max_landing_pad_size = #{maxLandingPadSize}, " +
            "market_updated_at = #{marketUpdatedAt} WHERE id = #{id}")
    void update(MybatisStationEntity station);

    @Delete("DELETE FROM station WHERE id = #{id}")
    void delete(@Param("id") UUID id);
}