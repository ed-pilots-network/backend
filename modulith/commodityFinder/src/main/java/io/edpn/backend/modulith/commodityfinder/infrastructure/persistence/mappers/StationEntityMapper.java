package io.edpn.backend.modulith.commodityfinder.infrastructure.persistence.mappers;

import io.edpn.backend.modulith.commodityfinder.application.dto.persistence.StationEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface StationEntityMapper {

    @Select("SELECT * FROM station WHERE id = #{id}")
    Optional<StationEntity> findById(@Param("id") UUID id);

    @Select("SELECT * FROM station WHERE system_id = #{systemId} AND name = #{name}")
    Optional<StationEntity> findBySystemIdAndStationName(@Param("systemId") UUID systemId, @Param("name") String name);

    @Select("SELECT * FROM station")
    List<StationEntity> findAll();

    @Insert("INSERT INTO station (id, market_id, name, system_id, planetary, require_odyssey, fleet_carrier, max_landing_pad_size, market_updated_at) " +
            "VALUES (#{id}, #{marketId}, #{name}, #{systemId.id}, #{planetary}, #{requireOdyssey}, #{fleetCarrier}, #{maxLandingPadSize}, #{marketUpdatedAt})")
    void insert(StationEntity station);

    @Update("UPDATE station SET market_id = #{marketId}, name = #{name}, system_id = #{systemId.id}, planetary = #{planetary}, " +
            "require_odyssey = #{requireOdyssey}, fleet_carrier = #{fleetCarrier}, max_landing_pad_size = #{maxLandingPadSize}, " +
            "market_updated_at = #{marketUpdatedAt} WHERE id = #{id}")
    void update(StationEntity station);

    @Delete("DELETE FROM station WHERE id = #{id}")
    void deleteById(@Param("id") UUID id);

}