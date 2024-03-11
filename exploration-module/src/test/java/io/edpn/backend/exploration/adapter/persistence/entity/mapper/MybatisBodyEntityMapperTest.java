package io.edpn.backend.exploration.adapter.persistence.entity.mapper;

import io.edpn.backend.exploration.adapter.persistence.entity.MybatisBodyEntity;
import io.edpn.backend.exploration.application.domain.Body;
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

    private MybatisBodyEntityMapper underTest;

    @Mock
    private MybatisRingEntityMapper mockRingEntityMapper;

    @Mock
    private MybatisSystemEntityMapper mockSystemEntityMapper;

    @BeforeEach
    public void setUp() {
        underTest = new MybatisBodyEntityMapper(mockRingEntityMapper, mockSystemEntityMapper);
    }


    @Test
    public void testMap_givenDto_shouldReturnDomainObject() {

        MybatisBodyEntity entity = MybatisBodyEntity.builder()
                .id(UUID.randomUUID())
                .arrivalDistance(137.2)
                .ascendingNode(300.5)
                .atmosphere("Body Atmosphere")
                .atmosphericComposition(new HashedMap<>(Map.of("Nitrogen", 95.5, "Oxygen", 5.5)))
                .axialTilt(0.0)
                .bodyComposition(new HashedMap<>(Map.of("Carbon", 95.5, "Silicon", 5.5)))
                .discovered(false)
                .mapped(true)
                .name("Body Name")
                .localId(123456L)
                .eccentricity(123456.1)
                .landable(true)
                .mass(357.5)
                .materials(Map.of("Carbon", 95.5, "Silicon", 5.5))
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


        assertThat(result.id(), equalTo(entity.getId()));
        assertThat(result.arrivalDistance(), equalTo(entity.getArrivalDistance()));
        assertThat(result.ascendingNode(), equalTo(entity.getAscendingNode()));
        assertThat(result.atmosphere(), equalTo(entity.getAtmosphere()));
        assertThat(result.atmosphericComposition(), equalTo(entity.getAtmosphericComposition()));
        assertThat(result.axialTilt(), equalTo(entity.getAxialTilt()));
        assertThat(result.bodyComposition(), equalTo(entity.getBodyComposition()));
        assertThat(result.discovered(), equalTo(entity.getDiscovered()));
        assertThat(result.mapped(), equalTo(entity.getMapped()));
        assertThat(result.name(), equalTo(entity.getName()));
        assertThat(result.localId(), equalTo(entity.getLocalId()));
        assertThat(result.eccentricity(), equalTo(entity.getEccentricity()));
        assertThat(result.landable(), equalTo(entity.getLandable()));
        assertThat(result.mass(), equalTo(entity.getMass()));
        assertThat(result.materials(), equalTo(entity.getMaterials()));
        assertThat(result.meanAnomaly(), equalTo(entity.getMeanAnomaly()));
        assertThat(result.orbitalInclination(), equalTo(entity.getOrbitalInclination()));
        assertThat(result.orbitalPeriod(), equalTo(entity.getOrbitalPeriod()));
        assertThat(result.parents(), equalTo(entity.getParents()));
        assertThat(result.periapsis(), equalTo(entity.getPeriapsis()));
        assertThat(result.planetClass(), equalTo(entity.getPlanetClass()));
        assertThat(result.radius(), equalTo(entity.getRadius()));
        assertThat(result.rotationPeriod(), equalTo(entity.getRotationPeriod()));
        assertThat(result.semiMajorAxis(), equalTo(entity.getSemiMajorAxis()));
        assertThat(result.surfaceGravity(), equalTo(entity.getSurfaceGravity()));
        assertThat(result.surfacePressure(), equalTo(entity.getSurfacePressure()));
        assertThat(result.surfaceTemperature(), equalTo(entity.getSurfaceTemperature()));
        assertThat(result.systemAddress(), equalTo(entity.getSystemAddress()));
        assertThat(result.terraformState(), equalTo(entity.getTerraformState()));
        assertThat(result.tidalLock(), equalTo(entity.getTidalLock()));
        assertThat(result.volcanism(), equalTo(entity.getVolcanism()));
        assertThat(result.horizons(), equalTo(entity.getHorizons()));
        assertThat(result.odyssey(), equalTo(entity.getOdyssey()));
        assertThat(result.estimatedScanValue(), equalTo(entity.getEstimatedScanValue()));

    }

    @Test
    public void testMap_givenDomainObject_shouldReturnEntity() {
        Body object = Body.builder()
                .id(UUID.randomUUID())
                .arrivalDistance(137.2)
                .ascendingNode(300.5)
                .atmosphere("Body Atmosphere")
                .atmosphericComposition(new HashedMap<>(Map.of("Nitrogen", 95.5, "Oxygen", 5.5)))
                .axialTilt(0.0)
                .bodyComposition(new HashedMap<>(Map.of("Carbon", 95.5, "Silicon", 5.5)))
                .discovered(false)
                .mapped(true)
                .name("Body Name")
                .localId(123456L)
                .eccentricity(123456.1)
                .landable(true)
                .mass(357.5)
                .materials(Map.of("Carbon", 95.5, "Silicon", 5.5))
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

        MybatisBodyEntity result = underTest.map(object);


        assertThat(result.getId(), equalTo(object.id()));
        assertThat(result.getArrivalDistance(), equalTo(object.arrivalDistance()));
        assertThat(result.getAscendingNode(), equalTo(object.ascendingNode()));
        assertThat(result.getAtmosphere(), equalTo(object.atmosphere()));
        assertThat(result.getAtmosphericComposition(), equalTo(object.atmosphericComposition()));
        assertThat(result.getAxialTilt(), equalTo(object.axialTilt()));
        assertThat(result.getBodyComposition(), equalTo(object.bodyComposition()));
        assertThat(result.getDiscovered(), equalTo(object.discovered()));
        assertThat(result.getMapped(), equalTo(object.mapped()));
        assertThat(result.getName(), equalTo(object.name()));
        assertThat(result.getLocalId(), equalTo(object.localId()));
        assertThat(result.getEccentricity(), equalTo(object.eccentricity()));
        assertThat(result.getLandable(), equalTo(object.landable()));
        assertThat(result.getMass(), equalTo(object.mass()));
        assertThat(result.getMaterials(), equalTo(object.materials()));
        assertThat(result.getMeanAnomaly(), equalTo(object.meanAnomaly()));
        assertThat(result.getOrbitalInclination(), equalTo(object.orbitalInclination()));
        assertThat(result.getOrbitalPeriod(), equalTo(object.orbitalPeriod()));
        assertThat(result.getParents(), equalTo(object.parents()));
        assertThat(result.getPeriapsis(), equalTo(object.periapsis()));
        assertThat(result.getPlanetClass(), equalTo(object.planetClass()));
        assertThat(result.getRadius(), equalTo(object.radius()));
        assertThat(result.getRotationPeriod(), equalTo(object.rotationPeriod()));
        assertThat(result.getSemiMajorAxis(), equalTo(object.semiMajorAxis()));
        assertThat(result.getSurfaceGravity(), equalTo(object.surfaceGravity()));
        assertThat(result.getSurfacePressure(), equalTo(object.surfacePressure()));
        assertThat(result.getSurfaceTemperature(), equalTo(object.surfaceTemperature()));
        assertThat(result.getSystemAddress(), equalTo(object.systemAddress()));
        assertThat(result.getTerraformState(), equalTo(object.terraformState()));
        assertThat(result.getTidalLock(), equalTo(object.tidalLock()));
        assertThat(result.getVolcanism(), equalTo(object.volcanism()));
        assertThat(result.getHorizons(), equalTo(object.horizons()));
        assertThat(result.getOdyssey(), equalTo(object.odyssey()));
        assertThat(result.getEstimatedScanValue(), equalTo(object.estimatedScanValue()));
    }

}