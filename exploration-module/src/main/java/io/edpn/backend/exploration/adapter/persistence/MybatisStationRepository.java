package io.edpn.backend.exploration.adapter.persistence;

import io.edpn.backend.exploration.adapter.persistence.entity.MybatisStationEntity;
import io.edpn.backend.exploration.adapter.persistence.entity.MybatisSystemEntity;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface MybatisStationRepository {

    @Select("""
            WITH upsert AS (
                INSERT INTO station (id, market_id, name, type, distance_from_star, system_id, economy, government, odyssey,
                                     updated_at)
                    VALUES (${id}, ${marketId}, ${name}, ${type}, ${distanceFromStar}, ${system.id}, ${economy}, ${government}, ${odyssey},
                            ${updatedAt})
                    ON CONFLICT (name, system_id)
                        DO UPDATE SET
                            market_id = COALESCE(EXCLUDED.market_id, station.market_id),
                            type = COALESCE(EXCLUDED.type, station.type),
                            distance_from_star = COALESCE(EXCLUDED.distance_from_star, station.distance_from_star),
                            economy = COALESCE(EXCLUDED.economy, station.economy),
                            government = COALESCE(EXCLUDED.government, station.government),
                            odyssey = COALESCE(EXCLUDED.odyssey, station.odyssey),
                            updated_at = COALESCE(EXCLUDED.updated_at, station.updated_at)
                            WHERE station.updated_at IS NULL OR station.updated_at < EXCLUDED.updated_at
                    RETURNING *
                    ),
                 delete_station_economies AS (
                     DELETE FROM station_economies
                         WHERE station_id = (SELECT id FROM upsert)
                    ),
                insert_new_station_economies AS (
                    INSERT INTO station_economies VALUES
                            <foreach item="key" index="key" collection="station.economies.keySet()" separator="," open="(" close=")">
                                ${id}, #{key}, #{station.economies[key]}
                            </foreach>
                           ON CONFLICT (station_id, economy_type)
                        DO UPDATE SET
                           ratio = EXCLUDED.ratio
                    ),
                 delete_station_landingpads AS (
                     DELETE FROM station_landingpads
                         WHERE station_id = (SELECT id FROM upsert)
                    ),
                insert_new_station_landingpads AS (
                    INSERT INTO station_landingpads VALUES
                            <foreach item="key" collection="station.landingPads.keySet()" separator="," open="(" close=")">
                                ${id}, #{key}, #{station.landingPads[key]}
                            </foreach>
                           ON CONFLICT (station_id, pad_size)
                        DO UPDATE SET
                           quantity = EXCLUDED.quantity
                    )
            SELECT *
            FROM upsert
            UNION ALL
            SELECT *
            FROM station
            WHERE NOT EXISTS (SELECT id FROM upsert)
              AND name = ${name}
              AND system_id = ${system.id}
                        """)
    @ResultMap("stationResultMap")
    MybatisStationEntity insertOrUpdateOnConflict(MybatisStationEntity station);

    @Select({
            "SELECT s.id, s.market_id, s.name, s.type, s.distance_from_star, s.system_id, s.economy, s.government, s.odyssey, s.updated_at ",
            "FROM station s ",
            "INNER JOIN system sys ON s.system_id = sys.id ",
            "WHERE sys.name = #{systemName} AND s.name = #{stationName}"
    })
    @Results(id = "stationResultMap", value = {
            @Result(property = "id", column = "id", javaType = UUID.class),
            @Result(property = "marketId", column = "market_id", javaType = Long.class),
            @Result(property = "name", column = "name", javaType = String.class),
            @Result(property = "type", column = "type", javaType = String.class),
            @Result(property = "distanceFromStar", column = "distance_from_star", javaType = Double.class),
            @Result(property = "system", column = "system_id", javaType = MybatisSystemEntity.class,
                    one = @One(select = "io.edpn.backend.exploration.adapter.persistence.MybatisSystemRepository.findById")),
            @Result(property = "economy", column = "economy", javaType = String.class),
            @Result(property = "government", column = "government", javaType = String.class),
            @Result(property = "odyssey", column = "odyssey", javaType = Boolean.class),
            @Result(property = "updatedAt", column = "updated_at", javaType = LocalDateTime.class),
            @Result(property = "landingPads", column = "id", javaType = Map.class,
                    many = @Many(select = "io.edpn.backend.exploration.adapter.persistence.MybatisStationRepository.findLandingPadsByStationId")),
            @Result(property = "economies", column = "id", javaType = Map.class,
                    many = @Many(select = "io.edpn.backend.exploration.adapter.persistence.MybatisStationRepository.findEconomiesByStationId"))
    })
    Optional<MybatisStationEntity> findByIdentifier(@Param("systemName") String systemName, @Param("stationName") String stationName);

    @Select("SELECT pad_size, quantity FROM station_landingpads WHERE station_id = #{stationId}")
    List<Map.Entry<String, Integer>> findLandingPadsByStationId(@Param("stationId") UUID stationId);

    @Select("SELECT economy_type, ratio FROM station_economies WHERE station_id = #{stationId}")
    List<Map.Entry<String, Double>> findEconomiesByStationId(@Param("stationId") UUID stationId);


}
