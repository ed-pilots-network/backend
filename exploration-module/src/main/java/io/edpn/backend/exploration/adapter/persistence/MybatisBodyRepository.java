package io.edpn.backend.exploration.adapter.persistence;

import io.edpn.backend.exploration.adapter.persistence.entity.MybatisBodyEntity;
import io.edpn.backend.exploration.adapter.persistence.entity.MybatisSystemEntity;
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
            "system_address, terraform_state, tidal_lock, volcanism, horizon, odyssey, estimated_scan_value)",
            "VALUES (#{id},#{arrivalDistance}, #{ascendingNode}, #{atmosphere}, #{atmosphericComposition}, #{axialTilt}, #{bodyComposition}," +
                    "#{discovered}, #{mapped}, #{name}, #{localId}, #{eccentricity}, #{landable}, #{mass}, #{materials}, #{meanAnomaly}," +
                    "#{orbitalInclination}, #{orbitalPeriod}, #{periapsis}, #{planetClass}, #{radius}, #{rings}, #{rotationPeriod}," +
                    "#{semiMajorAxis}, #{surfaceGravity}, #{surfacePressure}, #{surfaceTemperature}, #{system}, #{systemAddress}," +
                    "#{terraformState}, #{tidalLock}, #{volcanism}, #{horizons}, #{odyssey}, #{estimatedScanValue})",
            "ON CONFLICT (name)",
            "DO UPDATE SET",
            "id = COALESCE(body.id, EXCLUDED.id),",
            "arrival_distance = COALESCE(body.arrival_distance, EXCLUDED.arrival_distance),",
            "ascending_node = COALESCE(body.ascending_node, EXCLUDED.ascending_node),",
            "atmosphere = COALESCE(body.atmosphere, EXCLUDED.atmosphere),",
            "atmospheric_composition = COALESCE(body.atmospheric_composition, EXCLUDED.atmospheric_composition),",
            "axial_tilt = COALESCE(body.axial_tilt, EXCLUDED.axial_tilt),",
            "body_composition = COALESCE(body.body_composition, EXCLUDED.body_composition),",
            "discovered = COALESCE(body.discovered, EXCLUDED.discovered),",
            "mapped = COALESCE(body.mapped, EXCLUDED.mapped),",
            "name = COALESCE(body.name, EXCLUDED.name),",
            "local_id = COALESCE(body.local_id, EXCLUDED.local_id),",
            "eccentricity = COALESCE(body.eccentricity, EXCLUDED.eccentricity),",
            "landable = COALESCE(body.landable, EXCLUDED.landable),",
            "mass = COALESCE(body.mass, EXCLUDED.mass),",
            "materials = COALESCE(body.materials, EXCLUDED.materials),",
            "mean_anomaly = COALESCE(body.mean_anomaly, EXCLUDED.mean_anomaly),",
            "orbital_inclination = COALESCE(body.orbital_inclination, EXCLUDED.orbital_inclination),",
            "orbital_period = COALESCE(body.orbital_period, EXCLUDED.orbital_period),",
            "periapsis = COALESCE(body.periapsis, EXCLUDED.periapsis),",
            "planet_class = COALESCE(body.planet_class, EXCLUDED.planet_class),",
            "radius = COALESCE(body.radius, EXCLUDED.radius),",
            "rotation_period = COALESCE(body.rotation_period, EXCLUDED.rotation_period),",
            "semi_major_axis = COALESCE(body.semi_major_axis, EXCLUDED.semi_major_axis),",
            "surface_gravity = COALESCE(body.surface_gravity, EXCLUDED.surface_gravity),",
            "surface_pressure = COALESCE(body.surface_pressure, EXCLUDED.surface_pressure),",
            "surface_temperature = COALESCE(body.surface_temperature, EXCLUDED.surface_temperature),",
            "system_id = COALESCE(body.system_id, EXCLUDED.system_id),",
            "system_address = COALESCE(body.system_address, EXCLUDED.system_address),",
            "terraform_state = COALESCE(body.terraform_state, EXCLUDED.terraform_state),",
            "tidal_lock = COALESCE(body.tidal_lock, EXCLUDED.tidal_lock),",
            "volcanism = COALESCE(body.volcanism, EXCLUDED.volcanism),",
            "horizon = COALESCE(body.horizon, EXCLUDED.horizon),",
            "odyssey = COALESCE(body.odyssey, EXCLUDED.odyssey),",
            "estimated_scan_value = COALESCE(body.estimated_scan_value, EXCLUDED.estimated_scan_value)",
            "RETURNING *"
    })
    @ResultMap("bodyResultMap")
    MybatisBodyEntity insertOrUpdateOnConflict(MybatisBodyEntity body);
    
    
    @Select({"SELECT id, arrival_distance, ascending_node, atmosphere, atmospheric_composition, axial_tilt, body_composition," +
            "discovered, mapped, name, local_id, eccentricity, landable, mass, materials, mean_anomaly, orbital_inclination, orbital_period," +
            "periapsis, planet_class, radius, rotation_period, semi_major_axis, surface_gravity, surface_pressure, surface_temperature, system_id," +
            "system_address, terraform_state, tidal_lock, volcanism, horizon, odyssey, estimated_scan_value",
            "FROM body",
            "WHERE name = #{name}"}
    )
    @Results(id = "bodyResultMap", value = {
            @Result(property = "id", column = "id", javaType = UUID.class, typeHandler = UuidTypeHandler.class),
            @Result(property = "arrival_distance", column = "arrivalDistance", javaType = Double.class),
            @Result(property = "ascending_node", column = "ascendingNode", javaType = Double.class),
            @Result(property = "atmosphere", column = "atmosphere", javaType = String.class),
            @Result(property = "atmospheric_composition", column = "atmosphericComposition", javaType = Map.class),
            @Result(property = "axial_tilt", column = "axialTilt", javaType = Double.class),
            @Result(property = "body_composition", column = "bodyComposition", javaType = Map.class),
            @Result(property = "discovered", column = "discovered", javaType = Boolean.class),
            @Result(property = "mapped", column = "mapped", javaType = Boolean.class),
            @Result(property = "name", column = "name", javaType = String.class),
            @Result(property = "local_id", column = "localId", javaType = Long.class),
            @Result(property = "eccentricity", column = "eccentricity", javaType = Double.class),
            @Result(property = "landable", column = "landable", javaType = Boolean.class),
            @Result(property = "mass", column = "mass", javaType = Double.class),
            @Result(property = "materials", column = "materials", javaType = Map.class),
            @Result(property = "meanAnomaly", column = "meanAnomaly", javaType = Double.class),
            @Result(property = "orbital_inclination", column = "orbitalInclination", javaType = Double.class),
            @Result(property = "orbital_period", column = "orbitalPeriod", javaType = Double.class),
            @Result(property = "periapsis", column = "periapsis", javaType = Double.class),
            @Result(property = "planet_class", column = "planetClass", javaType = String.class),
            @Result(property = "radius", column = "radius", javaType = Double.class),
            @Result(property = "rings", column = "id", javaType = UUID.class, typeHandler = UuidTypeHandler.class,
                    many = @Many(select = "io.edpn.backend.exploration.adapter.persistence.MybatisRingRepository.findRingsFromBodyId")), //TODO
            @Result(property = "rotation_period", column = "rotationPeriod", javaType = Double.class),
            @Result(property = "semi_major_axis", column = "semiMajorAxis", javaType = Double.class),
            @Result(property = "surface_gravity", column = "surfaceGravity", javaType = Double.class),
            @Result(property = "surface_gressure", column = "surfacePressure", javaType = Double.class),
            @Result(property = "surface_temperature", column = "surfaceTemperature", javaType = Double.class),
            @Result(property = "system", column = "system", javaType = MybatisSystemEntity.class,
                    one = @One(select = "io.edpn.backend.exploration.adapter.persistence.MybatisSystemRepository.findById")),
            @Result(property = "system_address", column = "systemAddress", javaType = Long.class),
            @Result(property = "terraform_state", column = "terraformState", javaType = String.class),
            @Result(property = "tidal_lock", column = "tidalLock", javaType = Boolean.class),
            @Result(property = "volcanism", column = "volcanism", javaType = String.class),
            @Result(property = "horizons", column = "horizons", javaType = Boolean.class),
            @Result(property = "odyssey", column = "odyssey", javaType = Boolean.class),
            @Result(property = "estimated_scan_value", column = "estimatedScanValue", javaType = Double.class)
    })
    Optional<MybatisBodyEntity> findByName(@Param("name") String name);
    

}
