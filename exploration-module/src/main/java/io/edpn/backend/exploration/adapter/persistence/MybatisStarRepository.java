package io.edpn.backend.exploration.adapter.persistence;

import io.edpn.backend.exploration.adapter.persistence.entity.MybatisStarEntity;
import io.edpn.backend.exploration.adapter.persistence.entity.MybatisSystemEntity;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MybatisStarRepository {

    @Select({"INSERT INTO star (id, absolute_magnitude, age, arrival_distance, axial_tilt, discovered, local_id, luminosity, mapped, " +
            "name, radius, rotational_period, star_type, stellar_mass, subclass, surface_temperature, system_id, system_address, " +
            "horizons, odyssey) " +
            "VALUES (#{id}, #{absoluteMagnitude}, #{age}, #{arrivalDistance}, #{axialTilt}, #{discovered}, #{localId}, #{luminosity}, #{mapped}, " +
            "#{name}, #{radius}, #{rotationalPeriod}, #{starType}, #{stellarMass}, #{subclass}, #{surfaceTemperature}, #{system.id}, #{systemAddress}" +
            ", #{horizons}, #{odyssey})" +
            "ON CONFLICT (name, system_id)" +
            "DO UPDATE SET " +
            "absolute_magnitude = COALESCE(star.absolute_magnitude, EXCLUDED.absolute_magnitude)," +
            "age = COALESCE(star.age, EXCLUDED.age)," +
            "arrival_distance = COALESCE(star.arrival_distance, EXCLUDED.arrival_distance)," +
            "axial_tilt = COALESCE(star.axial_tilt, EXCLUDED.axial_tilt)," +
            "discovered = COALESCE(star.discovered, EXCLUDED.discovered)," +
            "local_id = COALESCE(star.local_id, EXCLUDED.local_id)," +
            "luminosity = COALESCE(star.luminosity, EXCLUDED.luminosity)," +
            "mapped = COALESCE(star.mapped, EXCLUDED.mapped)," +
            "radius = COALESCE(star.radius, EXCLUDED.radius)," +
            "rotational_period = COALESCE(star.rotational_period, EXCLUDED.rotational_period)," +
            "star_type = COALESCE(star.star_type, EXCLUDED.star_type)," +
            "stellar_mass = COALESCE(star.stellar_mass, EXCLUDED.stellar_mass)," +
            "subclass = COALESCE(star.subclass, EXCLUDED.subclass)," +
            "surface_temperature = COALESCE(star.surface_temperature, EXCLUDED.surface_temperature)," +
            "system_id = COALESCE(star.system_id, EXCLUDED.system_id)," +
            "system_address = COALESCE(star.system_address, EXCLUDED.system_address)," +
            "horizons = COALESCE(star.horizons, EXCLUDED.horizons)," +
            "odyssey = COALESCE(star.odyssey, EXCLUDED.odyssey) " +
            "RETURNING *"
    })
    @ResultMap("starResultMap")
    MybatisStarEntity insertOrUpdateOnConflict(MybatisStarEntity star);

    @Select({"SELECT id, absolute_magnitude, age, arrival_distance, axial_tilt, discovered, local_id, luminosity, mapped, " +
            "name, radius, rings, rotational_period, star_type, stellar_mass, subclass, surface_temperature, system, system_address, " +
            "horizons, odyssey, estimated_scan_value ",
            "FROM star",
            "WHERE name = #{name}"})
    @Results(id = "starResultMap", value = {
            @Result(property = "id", column = "id", javaType = UUID.class),
            @Result(property = "absoluteMagnitude", column = "absolute_magnitude", javaType = Double.class),
            @Result(property = "age", column = "age", javaType = Long.class),
            @Result(property = "arrivalDistance", column = "arrival_distance", javaType = Double.class),
            @Result(property = "axialTilt", column = "axial_tilt", javaType = Double.class),
            @Result(property = "discovered", column = "discovered", javaType = Boolean.class),
            @Result(property = "localId", column = "local_id", javaType = Long.class),
            @Result(property = "luminosity", column = "luminosity", javaType = String.class),
            @Result(property = "mapped", column = "mapped", javaType = Boolean.class),
            @Result(property = "name", column = "name", javaType = String.class),
            @Result(property = "radius", column = "radius", javaType = Double.class),
            @Result(property = "rings", column = "id", javaType = List.class,
                    many = @Many(select = "io.edpn.backend.exploration.adapter.persistence.MybatisRingRepository.findRingsByStarId")),
            @Result(property = "rotationalPeriod", column = "rotational_period", javaType = Double.class),
            @Result(property = "starType", column = "star_type", javaType = String.class),
            @Result(property = "stellarMass", column = "stellar_mass", javaType = Long.class),
            @Result(property = "subclass", column = "subclass", javaType = Integer.class),
            @Result(property = "surfaceTemperature", column = "surface_temperature", javaType = Double.class),
            @Result(property = "system", column = "system_id", javaType = MybatisSystemEntity.class,
                    one = @One(select = "io.edpn.backend.exploration.adapter.persistence.MybatisSystemRepository.findById")),
            @Result(property = "systemAddress", column = "system_address", javaType = Long.class),
            @Result(property = "horizons", column = "horizons", javaType = Boolean.class),
            @Result(property = "odyssey", column = "odyssey", javaType = Boolean.class),
            @Result(property = "estimatedScanValue", column = "estimated_scan_value", javaType = Double.class),
    })
    Optional<MybatisStarEntity> findByName(@Param("name") String name);

    @Select({"SELECT * FROM star WHERE id = #{id}"})
    @ResultMap("starResultMap")
    Optional<MybatisStarEntity> findById(UUID id);
}
