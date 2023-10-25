package io.edpn.backend.exploration.adapter.persistence;

import io.edpn.backend.exploration.adapter.persistence.entity.MybatisBodyEntity;
import io.edpn.backend.exploration.adapter.persistence.entity.MybatisRingEntity;
import io.edpn.backend.exploration.adapter.persistence.entity.MybatisSystemEntity;
import io.edpn.backend.mybatisutil.StringDoubleMapTypeHandler;
import io.edpn.backend.mybatisutil.UuidTypeHandler;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface MybatisBodyRepository {
    
    @Select({"INSERT INTO body (id, arrival_distance, ascending_node, atmosphere, atmospheric_composition, axial_tilt, body_composition," +
            "discovered, mapped, name, local_id, eccentricity, landable, mass, materials, mean_anomaly, orbital_inclination, orbital_period, " +
            "periapsis, planet_class, radius, rotation_period, semi_major_axis, surface_gravity, surface_pressure, surface_temperature, system_id," +
            "system_address, terraform_state, tidal_lock, volcanism, horizons, odyssey) " +
            "VALUES (#{id},#{arrivalDistance}, #{ascendingNode}, #{atmosphere}, #{atmosphericComposition}, #{axialTilt}, #{bodyComposition}," +
                    "#{discovered}, #{mapped}, #{name}, #{localId}, #{eccentricity}, #{landable}, #{mass}, #{materials}, #{meanAnomaly}," +
                    "#{orbitalInclination}, #{orbitalPeriod}, #{periapsis}, #{planetClass}, #{radius}, #{rotationPeriod}," +
                    "#{semiMajorAxis}, #{surfaceGravity}, #{surfacePressure}, #{surfaceTemperature}, #{system.id}, #{systemAddress}," +
                    "#{terraformState}, #{tidalLock}, #{volcanism}, #{horizons}, #{odyssey}) ",
            "ON CONFLICT (name, system_id) " +
            "DO UPDATE SET " +
            "arrival_distance = COALESCE(body.arrival_distance, EXCLUDED.arrival_distance)," +
            "ascending_node = COALESCE(body.ascending_node, EXCLUDED.ascending_node)," +
            "atmosphere = COALESCE(body.atmosphere, EXCLUDED.atmosphere)," +
            "atmospheric_composition = COALESCE(body.atmospheric_composition, EXCLUDED.atmospheric_composition)," +
            "axial_tilt = COALESCE(body.axial_tilt, EXCLUDED.axial_tilt)," +
            "body_composition = COALESCE(body.body_composition, EXCLUDED.body_composition)," +
            "discovered = COALESCE(body.discovered, EXCLUDED.discovered)," +
            "mapped = COALESCE(body.mapped, EXCLUDED.mapped)," +
            "local_id = COALESCE(body.local_id, EXCLUDED.local_id)," +
            "eccentricity = COALESCE(body.eccentricity, EXCLUDED.eccentricity)," +
            "landable = COALESCE(body.landable, EXCLUDED.landable)," +
            "mass = COALESCE(body.mass, EXCLUDED.mass)," +
            "materials = COALESCE(body.materials, EXCLUDED.materials)," +
            "mean_anomaly = COALESCE(body.mean_anomaly, EXCLUDED.mean_anomaly)," +
            "orbital_inclination = COALESCE(body.orbital_inclination, EXCLUDED.orbital_inclination)," +
            "orbital_period = COALESCE(body.orbital_period, EXCLUDED.orbital_period)," +
            "periapsis = COALESCE(body.periapsis, EXCLUDED.periapsis)," +
            "planet_class = COALESCE(body.planet_class, EXCLUDED.planet_class)," +
            "radius = COALESCE(body.radius, EXCLUDED.radius)," +
            "rotation_period = COALESCE(body.rotation_period, EXCLUDED.rotation_period)," +
            "semi_major_axis = COALESCE(body.semi_major_axis, EXCLUDED.semi_major_axis)," +
            "surface_gravity = COALESCE(body.surface_gravity, EXCLUDED.surface_gravity)," +
            "surface_pressure = COALESCE(body.surface_pressure, EXCLUDED.surface_pressure)," +
            "surface_temperature = COALESCE(body.surface_temperature, EXCLUDED.surface_temperature)," +
            "system_id = COALESCE(body.system_id, EXCLUDED.system_id)," +
            "system_address = COALESCE(body.system_address, EXCLUDED.system_address)," +
            "terraform_state = COALESCE(body.terraform_state, EXCLUDED.terraform_state)," +
            "tidal_lock = COALESCE(body.tidal_lock, EXCLUDED.tidal_lock)," +
            "volcanism = COALESCE(body.volcanism, EXCLUDED.volcanism)," +
            "horizons = COALESCE(body.horizons, EXCLUDED.horizons)," +
            "odyssey = COALESCE(body.odyssey, EXCLUDED.odyssey) " +
            "RETURNING *"
    })
    @ResultMap("bodyResultMap")
    MybatisBodyEntity insertOrUpdateOnConflict(MybatisBodyEntity body);
    
    
    @Select({"SELECT * " +
            "FROM body" +
            "WHERE name = #{name}"}
    )
    @Results(id = "bodyResultMap", value = {
            @Result(property = "id", column = "id", javaType = UUID.class, typeHandler = UuidTypeHandler.class),
            @Result(property = "arrivalDistance", column = "arrival_distance", javaType = Double.class),
            @Result(property = "ascendingNode", column = "ascending_node", javaType = Double.class),
            @Result(property = "atmosphere", column = "atmosphere", javaType = String.class),
            @Result(property = "atmosphericComposition", column = "atmospheric_composition", javaType = Map.class, typeHandler = StringDoubleMapTypeHandler.class),
            @Result(property = "axialTilt", column = "axial_tilt", javaType = Double.class),
            @Result(property = "bodyComposition", column = "body_composition", javaType = Map.class, typeHandler = StringDoubleMapTypeHandler.class),
            @Result(property = "discovered", column = "discovered", javaType = Boolean.class),
            @Result(property = "mapped", column = "mapped", javaType = Boolean.class),
            @Result(property = "name", column = "name", javaType = String.class),
            @Result(property = "localId", column = "local_id", javaType = Long.class),
            @Result(property = "eccentricity", column = "eccentricity", javaType = Double.class),
            @Result(property = "landable", column = "landable", javaType = Boolean.class),
            @Result(property = "mass", column = "mass", javaType = Double.class),
            @Result(property = "materials", column = "materials", javaType = Map.class, typeHandler = StringDoubleMapTypeHandler.class),
            @Result(property = "meanAnomaly", column = "meanAnomaly", javaType = Double.class),
            @Result(property = "orbitalInclination", column = "orbital_inclination", javaType = Double.class),
            @Result(property = "orbital_period", column = "orbitalPeriod", javaType = Double.class),
            @Result(property = "periapsis", column = "periapsis", javaType = Double.class),
            @Result(property = "planetClass", column = "planet_class", javaType = String.class),
            @Result(property = "radius", column = "radius", javaType = Double.class),
            @Result(property = "rings", column = "id", javaType = MybatisRingEntity.class,
                    many = @Many(select = "io.edpn.backend.exploration.adapter.persistence.MybatisRingRepository.findRingsByBodyId")), //TODO
            @Result(property = "rotationPeriod", column = "rotation_period", javaType = Double.class),
            @Result(property = "semiMajorAxis", column = "semi_major_axis", javaType = Double.class),
            @Result(property = "surfaceGravity", column = "surface_gravity", javaType = Double.class),
            @Result(property = "surfacePressure", column = "surface_pressure", javaType = Double.class),
            @Result(property = "surfaceTemperature", column = "surface_temperature", javaType = Double.class),
            @Result(property = "system", column = "system_id", javaType = MybatisSystemEntity.class,
                    one = @One(select = "io.edpn.backend.exploration.adapter.persistence.MybatisSystemRepository.findById")),
            @Result(property = "systemAddress", column = "system_address", javaType = Long.class),
            @Result(property = "terraformState", column = "terraform_state", javaType = String.class),
            @Result(property = "tidalLock", column = "tidal_lock", javaType = Boolean.class),
            @Result(property = "volcanism", column = "volcanism", javaType = String.class),
            @Result(property = "horizons", column = "horizons", javaType = Boolean.class),
            @Result(property = "odyssey", column = "odyssey", javaType = Boolean.class),
            @Result(property = "estimatedScanValue", column = "estimated_scan_value", javaType = Double.class)
    })
    Optional<MybatisBodyEntity> findByName(@Param("name") String name);
    
    @Select({"SELECT * FROM body WHERE id = #{id}"})
    @ResultMap("bodyResultMap")
    Optional<MybatisBodyEntity> findById(UUID id);
    

}
