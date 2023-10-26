package io.edpn.backend.exploration.adapter.persistence.entity.mapper;

import io.edpn.backend.exploration.adapter.persistence.entity.MybatisRingEntity;
import io.edpn.backend.exploration.adapter.persistence.entity.MybatisStarEntity;
import io.edpn.backend.exploration.adapter.persistence.entity.MybatisSystemEntity;
import io.edpn.backend.exploration.application.domain.Star;
import io.edpn.backend.exploration.application.dto.persistence.entity.StarEntity;
import io.edpn.backend.exploration.application.dto.persistence.entity.mapper.RingEntityMapper;
import io.edpn.backend.exploration.application.dto.persistence.entity.mapper.StarEntityMapper;
import io.edpn.backend.exploration.application.dto.persistence.entity.mapper.SystemEntityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class MybatisStarEntityMapperTest {
    
    private StarEntityMapper<MybatisStarEntity> underTest;
    
    @Mock
    private RingEntityMapper<MybatisRingEntity> mockRingEntityMapper;
    
    @Mock
    private SystemEntityMapper<MybatisSystemEntity> mockSystemEntityMapper;
    
    @BeforeEach
    public void setUp() {
        underTest = new MybatisStarEntityMapper(mockRingEntityMapper, mockSystemEntityMapper);
    }
    
    
    @Test
    public void testMap_givenDto_shouldReturnDomainObject() {
        StarEntity entity = MybatisStarEntity.builder()
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
        
        assertThat(result.getId(), equalTo(entity.getId()));
        assertThat(result.getAbsoluteMagnitude(), equalTo(entity.getAbsoluteMagnitude()));
        assertThat(result.getAge(), equalTo(entity.getAge()));
        assertThat(result.getArrivalDistance(), equalTo(entity.getArrivalDistance()));
        assertThat(result.getAxialTilt(), equalTo(entity.getAxialTilt()));
        assertThat(result.getDiscovered(), equalTo(entity.getDiscovered()));
        assertThat(result.getLocalId(), equalTo(entity.getLocalId()));
        assertThat(result.getLuminosity(), equalTo(entity.getLuminosity()));
        assertThat(result.getMapped(), equalTo(entity.getMapped()));
        assertThat(result.getName(), equalTo(entity.getName()));
        assertThat(result.getRadius(), equalTo(entity.getRadius()));
        assertThat(result.getRotationalPeriod(), equalTo(entity.getRotationalPeriod()));
        assertThat(result.getStarType(), equalTo(entity.getStarType()));
        assertThat(result.getStellarMass(), equalTo(entity.getStellarMass()));
        assertThat(result.getSubclass(), equalTo(entity.getSubclass()));
        assertThat(result.getSurfaceTemperature(), equalTo(entity.getSurfaceTemperature()));
        assertThat(result.getSystemAddress(), equalTo(entity.getSystemAddress()));
        assertThat(result.getHorizons(), equalTo(entity.getHorizons()));
        assertThat(result.getOdyssey(), equalTo(entity.getOdyssey()));
        assertThat(result.getEstimatedScanValue(), equalTo(entity.getEstimatedScanValue()));
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
        
        StarEntity result = underTest.map(object);
        
        assertThat(result.getId(), equalTo(object.getId()));
        assertThat(result.getAbsoluteMagnitude(), equalTo(object.getAbsoluteMagnitude()));
        assertThat(result.getAge(), equalTo(object.getAge()));
        assertThat(result.getArrivalDistance(), equalTo(object.getArrivalDistance()));
        assertThat(result.getAxialTilt(), equalTo(object.getAxialTilt()));
        assertThat(result.getDiscovered(), equalTo(object.getDiscovered()));
        assertThat(result.getLocalId(), equalTo(object.getLocalId()));
        assertThat(result.getLuminosity(), equalTo(object.getLuminosity()));
        assertThat(result.getMapped(), equalTo(object.getMapped()));
        assertThat(result.getName(), equalTo(object.getName()));
        assertThat(result.getRadius(), equalTo(object.getRadius()));
        assertThat(result.getRotationalPeriod(), equalTo(object.getRotationalPeriod()));
        assertThat(result.getStarType(), equalTo(object.getStarType()));
        assertThat(result.getStellarMass(), equalTo(object.getStellarMass()));
        assertThat(result.getSubclass(), equalTo(object.getSubclass()));
        assertThat(result.getSurfaceTemperature(), equalTo(object.getSurfaceTemperature()));
        assertThat(result.getSystemAddress(), equalTo(object.getSystemAddress()));
        assertThat(result.getHorizons(), equalTo(object.getHorizons()));
        assertThat(result.getOdyssey(), equalTo(object.getOdyssey()));
        assertThat(result.getEstimatedScanValue(), equalTo(object.getEstimatedScanValue()));
    }
}