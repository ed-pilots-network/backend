package io.edpn.backend.exploration.adapter.persistence.entity.mapper;

import io.edpn.backend.exploration.adapter.persistence.entity.MybatisStarEntity;
import io.edpn.backend.exploration.application.domain.Star;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(MockitoExtension.class)
class MybatisStarEntityMapperTest {

    private MybatisStarEntityMapper underTest;

    @Mock
    private MybatisRingEntityMapper mockRingEntityMapper;

    @Mock
    private MybatisSystemEntityMapper mockSystemEntityMapper;

    @BeforeEach
    public void setUp() {
        underTest = new MybatisStarEntityMapper(mockRingEntityMapper, mockSystemEntityMapper);
    }


    @Test
    public void testMap_givenDto_shouldReturnDomainObject() {
        MybatisStarEntity entity = MybatisStarEntity.builder()
                .id(UUID.randomUUID())
                .absoluteMagnitude(12345.5)
                .age(12345L)
                .arrivalDistance(6543.5)
                .axialTilt(1.5)
                .discovered(true)
                .localId(987L)
                .luminosity("Very")
                .mapped(true)
                .name("Star Name")
                .radius(6543.5)
                .rotationalPeriod(1234.5)
                .starType("F")
                .stellarMass(1564L)
                .subclass(1)
                .surfaceTemperature(1234.9)
                .systemAddress(1L)
                .horizons(true)
                .odyssey(true)
                .estimatedScanValue(987.5)
                .build();

        Star result = underTest.map(entity);

        assertThat(result.id(), equalTo(entity.getId()));
        assertThat(result.absoluteMagnitude(), equalTo(entity.getAbsoluteMagnitude()));
        assertThat(result.age(), equalTo(entity.getAge()));
        assertThat(result.arrivalDistance(), equalTo(entity.getArrivalDistance()));
        assertThat(result.axialTilt(), equalTo(entity.getAxialTilt()));
        assertThat(result.discovered(), equalTo(entity.getDiscovered()));
        assertThat(result.localId(), equalTo(entity.getLocalId()));
        assertThat(result.luminosity(), equalTo(entity.getLuminosity()));
        assertThat(result.mapped(), equalTo(entity.getMapped()));
        assertThat(result.name(), equalTo(entity.getName()));
        assertThat(result.radius(), equalTo(entity.getRadius()));
        assertThat(result.rotationalPeriod(), equalTo(entity.getRotationalPeriod()));
        assertThat(result.starType(), equalTo(entity.getStarType()));
        assertThat(result.stellarMass(), equalTo(entity.getStellarMass()));
        assertThat(result.subclass(), equalTo(entity.getSubclass()));
        assertThat(result.surfaceTemperature(), equalTo(entity.getSurfaceTemperature()));
        assertThat(result.systemAddress(), equalTo(entity.getSystemAddress()));
        assertThat(result.horizons(), equalTo(entity.getHorizons()));
        assertThat(result.odyssey(), equalTo(entity.getOdyssey()));
        assertThat(result.estimatedScanValue(), equalTo(entity.getEstimatedScanValue()));
    }

    @Test
    public void testMap_givenDomainObject_shouldReturnEntity() {
        Star object = Star.builder()
                .id(UUID.randomUUID())
                .absoluteMagnitude(12345.5)
                .age(12345L)
                .arrivalDistance(6543.5)
                .axialTilt(1.5)
                .discovered(true)
                .localId(987L)
                .luminosity("Very")
                .mapped(true)
                .name("Star Name")
                .radius(6543.5)
                .rotationalPeriod(1234.5)
                .starType("F")
                .stellarMass(1564L)
                .subclass(1)
                .surfaceTemperature(1234.9)
                .systemAddress(1L)
                .horizons(true)
                .odyssey(true)
                .estimatedScanValue(987.5)
                .build();

        MybatisStarEntity result = underTest.map(object);

        assertThat(result.getId(), equalTo(object.id()));
        assertThat(result.getAbsoluteMagnitude(), equalTo(object.absoluteMagnitude()));
        assertThat(result.getAge(), equalTo(object.age()));
        assertThat(result.getArrivalDistance(), equalTo(object.arrivalDistance()));
        assertThat(result.getAxialTilt(), equalTo(object.axialTilt()));
        assertThat(result.getDiscovered(), equalTo(object.discovered()));
        assertThat(result.getLocalId(), equalTo(object.localId()));
        assertThat(result.getLuminosity(), equalTo(object.luminosity()));
        assertThat(result.getMapped(), equalTo(object.mapped()));
        assertThat(result.getName(), equalTo(object.name()));
        assertThat(result.getRadius(), equalTo(object.radius()));
        assertThat(result.getRotationalPeriod(), equalTo(object.rotationalPeriod()));
        assertThat(result.getStarType(), equalTo(object.starType()));
        assertThat(result.getStellarMass(), equalTo(object.stellarMass()));
        assertThat(result.getSubclass(), equalTo(object.subclass()));
        assertThat(result.getSurfaceTemperature(), equalTo(object.surfaceTemperature()));
        assertThat(result.getSystemAddress(), equalTo(object.systemAddress()));
        assertThat(result.getHorizons(), equalTo(object.horizons()));
        assertThat(result.getOdyssey(), equalTo(object.odyssey()));
        assertThat(result.getEstimatedScanValue(), equalTo(object.estimatedScanValue()));
    }
}