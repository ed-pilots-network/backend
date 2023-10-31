package io.edpn.backend.exploration.adapter.persistence.entity.mapper;

import io.edpn.backend.exploration.adapter.persistence.entity.MybatisRingEntity;
import io.edpn.backend.exploration.application.domain.Ring;
import io.edpn.backend.exploration.application.dto.persistence.entity.RingEntity;
import io.edpn.backend.exploration.application.dto.persistence.entity.mapper.RingEntityMapper;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

        assertThat(result.id(), equalTo(entity.getId()));
        assertThat(result.innerRadius(), equalTo(entity.getInnerRadius()));
        assertThat(result.mass(), equalTo(entity.getMass()));
        assertThat(result.name(), equalTo(entity.getName()));
        assertThat(result.outerRadius(), equalTo(entity.getOuterRadius()));
        assertThat(result.ringClass(), equalTo(entity.getRingClass()));
        assertThat(result.bodyId(), equalTo(entity.getBodyId()));
        assertThat(result.starId(), equalTo(entity.getStarId()));
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

        assertThat(result.getId(), equalTo(object.id()));
        assertThat(result.getInnerRadius(), equalTo(object.innerRadius()));
        assertThat(result.getMass(), equalTo(object.mass()));
        assertThat(result.getName(), equalTo(object.name()));
        assertThat(result.getOuterRadius(), equalTo(object.outerRadius()));
        assertThat(result.getRingClass(), equalTo(object.ringClass()));
        assertThat(result.getBodyId(), equalTo(object.bodyId()));
        assertThat(result.getStarId(), equalTo(object.starId()));
    }
}