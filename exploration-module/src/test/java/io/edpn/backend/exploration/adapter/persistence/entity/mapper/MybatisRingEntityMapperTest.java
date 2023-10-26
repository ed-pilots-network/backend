package io.edpn.backend.exploration.adapter.persistence.entity.mapper;

import io.edpn.backend.exploration.adapter.persistence.entity.MybatisRingEntity;
import io.edpn.backend.exploration.application.domain.Ring;
import io.edpn.backend.exploration.application.dto.persistence.entity.RingEntity;
import io.edpn.backend.exploration.application.dto.persistence.entity.mapper.RingEntityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


class MybatisRingEntityMapperTest {
    
    RingEntityMapper<MybatisRingEntity> underTest;
    
    @BeforeEach
    public void setUp(){
        underTest = new MybatisRingEntityMapper();
    }
    
    
    @Test
    public void testMap_givenDto_shouldReturnDomainObject() {
        RingEntity entity = MybatisRingEntity.builder()
                .id(UUID.randomUUID())
                .innerRadius(465L)
                .mass(123456L)
                .name("RingName")
                .outerRadius(9875L)
                .ringClass("L")
                .bodyId(UUID.randomUUID())
                .starId(UUID.randomUUID())
                .build();
        
        Ring result = underTest.map(entity);
        
        assertThat(result.getId(), equalTo(entity.getId()));
        assertThat(result.getInnerRadius(), equalTo(entity.getInnerRadius()));
        assertThat(result.getMass(), equalTo(entity.getMass()));
        assertThat(result.getName(), equalTo(entity.getName()));
        assertThat(result.getOuterRadius(), equalTo(entity.getOuterRadius()));
        assertThat(result.getRingClass(), equalTo(entity.getRingClass()));
        assertThat(result.getBodyId(), equalTo(entity.getBodyId()));
        assertThat(result.getStarId(), equalTo(entity.getStarId()));
    }
    
    @Test
    public void testMap_givenDomainObject_shouldReturnEntity() {
        Ring object = Ring.builder()
                .id(UUID.randomUUID())
                .innerRadius(465L)
                .mass(123456L)
                .name("RingName")
                .outerRadius(9875L)
                .ringClass("L")
                .bodyId(UUID.randomUUID())
                .starId(UUID.randomUUID())
                .build();
        
        RingEntity result = underTest.map(object);
        
        assertThat(result.getId(), equalTo(object.getId()));
        assertThat(result.getInnerRadius(), equalTo(object.getInnerRadius()));
        assertThat(result.getMass(), equalTo(object.getMass()));
        assertThat(result.getName(), equalTo(object.getName()));
        assertThat(result.getOuterRadius(), equalTo(object.getOuterRadius()));
        assertThat(result.getRingClass(), equalTo(object.getRingClass()));
        assertThat(result.getBodyId(), equalTo(object.getBodyId()));
        assertThat(result.getStarId(), equalTo(object.getStarId()));
    }
}