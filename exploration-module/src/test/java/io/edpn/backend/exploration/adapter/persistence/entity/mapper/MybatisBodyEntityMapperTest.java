package io.edpn.backend.exploration.adapter.persistence.entity.mapper;

import io.edpn.backend.exploration.adapter.persistence.entity.MybatisBodyEntity;
import io.edpn.backend.exploration.adapter.persistence.entity.MybatisRingEntity;
import io.edpn.backend.exploration.adapter.persistence.entity.MybatisSystemEntity;
import io.edpn.backend.exploration.application.domain.Body;
import io.edpn.backend.exploration.application.dto.persistence.entity.BodyEntity;
import io.edpn.backend.exploration.application.dto.persistence.entity.mapper.BodyEntityMapper;
import io.edpn.backend.exploration.application.dto.persistence.entity.mapper.RingEntityMapper;
import io.edpn.backend.exploration.application.dto.persistence.entity.mapper.SystemEntityMapper;
import org.apache.commons.collections4.map.HashedMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


@ExtendWith(MockitoExtension.class)
class MybatisBodyEntityMapperTest {
    
    private BodyEntityMapper<MybatisBodyEntity> underTest;
    
    @Mock
    private RingEntityMapper<MybatisRingEntity> mockRingEntityMapper;
    
    @Mock
    private SystemEntityMapper<MybatisSystemEntity> mockSystemEntityMapper;
    
    @BeforeEach
    public void setUp(){
        underTest = new MybatisBodyEntityMapper(mockRingEntityMapper, mockSystemEntityMapper);
    }
    
    
    @Test
    public void testMap_givenDto_shouldReturnDomainObject() {
        
        BodyEntity entity = MybatisBodyEntity.builder()
                .id(UUID.randomUUID())
                .arrivalDistance(137.2)
                .ascendingNode(300.5)
                .atmosphere("Body Atmosphere")
                .atmosphericComposition(new HashedMap<>(Map.of("Nitrogen", 95.5 ,"Oxygen", 5.5)))
                .axialTilt(0.0)
                .bodyComposition(new HashedMap<>(Map.of("Carbon", 95.5 ,"Silicon", 5.5)))
                .discovered(false)
                .mapped(true)
                .name("Body Name")
                .localId(123456L)
                .eccentricity(123456.1)
                .landable(true)
                .mass(357.5)
                .materials(Map.of("Carbon", 95.5 ,"Silicon", 5.5))
                .meanAnomaly(12345.1)
                .orbitalInclination(2.5)
                .orbitalPeriod(35.5)
                .parents(null)
                .periapsis(987654.2)
                .planetClass("M")
                .radius(4500.0)
                .rotationPeriod(654.5)
                .semiMajorAxis(32.5)
                .surfaceGravity(9.8)
                .surfacePressure(1.5)
                .surfaceTemperature(35.2)
                .systemAddress(465654L)
                .terraformState("Terraformable")
                .tidalLock(true)
                .volcanism("Volcanic")
                .horizons(true)
                .odyssey(true)
                .estimatedScanValue(35.5)
                .build();
        
        Body result = underTest.map(entity);
        
        
        assertThat(result.getId(), equalTo(entity.getId()));
        assertThat(result.getArrivalDistance(), equalTo(entity.getArrivalDistance()));
        assertThat(result.getAscendingNode(), equalTo(entity.getAscendingNode()));
        assertThat(result.getAtmosphere(), equalTo(entity.getAtmosphere()));
        assertThat(result.getAtmosphericComposition(), equalTo(entity.getAtmosphericComposition()));
        assertThat(result.getAxialTilt(), equalTo(entity.getAxialTilt()));
        assertThat(result.getBodyComposition(), equalTo(entity.getBodyComposition()));
        assertThat(result.getDiscovered(), equalTo(entity.getDiscovered()));
        assertThat(result.getMapped(), equalTo(entity.getMapped()));
        assertThat(result.getName(), equalTo(entity.getName()));
        assertThat(result.getLocalId(), equalTo(entity.getLocalId()));
        assertThat(result.getEccentricity(), equalTo(entity.getEccentricity()));
        assertThat(result.getLandable(), equalTo(entity.getLandable()));
        assertThat(result.getMass(), equalTo(entity.getMass()));
        assertThat(result.getMaterials(), equalTo(entity.getMaterials()));
        assertThat(result.getMeanAnomaly(), equalTo(entity.getMeanAnomaly()));
        assertThat(result.getOrbitalInclination(), equalTo(entity.getOrbitalInclination()));
        assertThat(result.getOrbitalPeriod(), equalTo(entity.getOrbitalPeriod()));
        assertThat(result.getParents(), equalTo(entity.getParents()));
        assertThat(result.getPeriapsis(), equalTo(entity.getPeriapsis()));
        assertThat(result.getPlanetClass(), equalTo(entity.getPlanetClass()));
        assertThat(result.getRadius(), equalTo(entity.getRadius()));
        assertThat(result.getRotationPeriod(), equalTo(entity.getRotationPeriod()));
        assertThat(result.getSemiMajorAxis(), equalTo(entity.getSemiMajorAxis()));
        assertThat(result.getSurfaceGravity(), equalTo(entity.getSurfaceGravity()));
        assertThat(result.getSurfacePressure(), equalTo(entity.getSurfacePressure()));
        assertThat(result.getSurfaceTemperature(), equalTo(entity.getSurfaceTemperature()));
        assertThat(result.getSystemAddress(), equalTo(entity.getSystemAddress()));
        assertThat(result.getTerraformState(), equalTo(entity.getTerraformState()));
        assertThat(result.getTidalLock(), equalTo(entity.getTidalLock()));
        assertThat(result.getVolcanism(), equalTo(entity.getVolcanism()));
        assertThat(result.getHorizons(), equalTo(entity.getHorizons()));
        assertThat(result.getOdyssey(), equalTo(entity.getOdyssey()));
        assertThat(result.getEstimatedScanValue(), equalTo(entity.getEstimatedScanValue()));
        
    }
    
    @Test
    public void testMap_givenDomainObject_shouldReturnEntity() {
        Body object = Body.builder()
                .id(UUID.randomUUID())
                .arrivalDistance(137.2)
                .ascendingNode(300.5)
                .atmosphere("Body Atmosphere")
                .atmosphericComposition(new HashedMap<>(Map.of("Nitrogen", 95.5 ,"Oxygen", 5.5)))
                .axialTilt(0.0)
                .bodyComposition(new HashedMap<>(Map.of("Carbon", 95.5 ,"Silicon", 5.5)))
                .discovered(false)
                .mapped(true)
                .name("Body Name")
                .localId(123456L)
                .eccentricity(123456.1)
                .landable(true)
                .mass(357.5)
                .materials(Map.of("Carbon", 95.5 ,"Silicon", 5.5))
                .meanAnomaly(12345.1)
                .orbitalInclination(2.5)
                .orbitalPeriod(35.5)
                .parents(null)
                .periapsis(987654.2)
                .planetClass("M")
                .radius(4500.0)
                .rotationPeriod(654.5)
                .semiMajorAxis(32.5)
                .surfaceGravity(9.8)
                .surfacePressure(1.5)
                .surfaceTemperature(35.2)
                .systemAddress(465654L)
                .terraformState("Terraformable")
                .tidalLock(true)
                .volcanism("Volcanic")
                .horizons(true)
                .odyssey(true)
                .estimatedScanValue(35.5)
                .build();
        
        BodyEntity result = underTest.map(object);
        
        
        assertThat(result.getId(), equalTo(object.getId()));
        assertThat(result.getArrivalDistance(), equalTo(object.getArrivalDistance()));
        assertThat(result.getAscendingNode(), equalTo(object.getAscendingNode()));
        assertThat(result.getAtmosphere(), equalTo(object.getAtmosphere()));
        assertThat(result.getAtmosphericComposition(), equalTo(object.getAtmosphericComposition()));
        assertThat(result.getAxialTilt(), equalTo(object.getAxialTilt()));
        assertThat(result.getBodyComposition(), equalTo(object.getBodyComposition()));
        assertThat(result.getDiscovered(), equalTo(object.getDiscovered()));
        assertThat(result.getMapped(), equalTo(object.getMapped()));
        assertThat(result.getName(), equalTo(object.getName()));
        assertThat(result.getLocalId(), equalTo(object.getLocalId()));
        assertThat(result.getEccentricity(), equalTo(object.getEccentricity()));
        assertThat(result.getLandable(), equalTo(object.getLandable()));
        assertThat(result.getMass(), equalTo(object.getMass()));
        assertThat(result.getMaterials(), equalTo(object.getMaterials()));
        assertThat(result.getMeanAnomaly(), equalTo(object.getMeanAnomaly()));
        assertThat(result.getOrbitalInclination(), equalTo(object.getOrbitalInclination()));
        assertThat(result.getOrbitalPeriod(), equalTo(object.getOrbitalPeriod()));
        assertThat(result.getParents(), equalTo(object.getParents()));
        assertThat(result.getPeriapsis(), equalTo(object.getPeriapsis()));
        assertThat(result.getPlanetClass(), equalTo(object.getPlanetClass()));
        assertThat(result.getRadius(), equalTo(object.getRadius()));
        assertThat(result.getRotationPeriod(), equalTo(object.getRotationPeriod()));
        assertThat(result.getSemiMajorAxis(), equalTo(object.getSemiMajorAxis()));
        assertThat(result.getSurfaceGravity(), equalTo(object.getSurfaceGravity()));
        assertThat(result.getSurfacePressure(), equalTo(object.getSurfacePressure()));
        assertThat(result.getSurfaceTemperature(), equalTo(object.getSurfaceTemperature()));
        assertThat(result.getSystemAddress(), equalTo(object.getSystemAddress()));
        assertThat(result.getTerraformState(), equalTo(object.getTerraformState()));
        assertThat(result.getTidalLock(), equalTo(object.getTidalLock()));
        assertThat(result.getVolcanism(), equalTo(object.getVolcanism()));
        assertThat(result.getHorizons(), equalTo(object.getHorizons()));
        assertThat(result.getOdyssey(), equalTo(object.getOdyssey()));
        assertThat(result.getEstimatedScanValue(), equalTo(object.getEstimatedScanValue()));
    }
    
}