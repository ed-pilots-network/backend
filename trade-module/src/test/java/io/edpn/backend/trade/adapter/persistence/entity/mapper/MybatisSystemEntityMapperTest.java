package io.edpn.backend.trade.adapter.persistence.entity.mapper;

import io.edpn.backend.trade.adapter.persistence.entity.MybatisSystemEntity;
import io.edpn.backend.trade.application.domain.Coordinate;
import io.edpn.backend.trade.application.domain.System;
import io.edpn.backend.trade.application.dto.persistence.entity.mapper.SystemEntityMapper;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@ExtendWith(MockitoExtension.class)
class MybatisSystemEntityMapperTest {
    
    private SystemEntityMapper<MybatisSystemEntity> underTest;
    
    @BeforeEach
    public void setUp() {
        underTest = new MybatisSystemEntityMapper();
    }
    
    @Test
    public void testMap_givenEntity_shouldReturnDomainObject() {
        UUID id = UUID.randomUUID();
        Long eliteId = 123456L;
        String name = "System Name";
        Double xCoordinate = 123.45;
        Double yCoordinate = 678.90;
        Double zCoordinate = 234.56;
        MybatisSystemEntity entity = MybatisSystemEntity.builder()
                .id(id)
                .eliteId(eliteId)
                .name(name)
                .xCoordinate(xCoordinate)
                .yCoordinate(yCoordinate)
                .zCoordinate(zCoordinate)
                .build();
        
        System domainObject = underTest.map(entity);

        assertThat(domainObject.id(), is(id));
        assertThat(domainObject.eliteId(), is(eliteId));
        assertThat(domainObject.name(), is(name));
        assertThat(domainObject.coordinate(), notNullValue());
        assertThat(domainObject.coordinate().x(), is(xCoordinate));
        assertThat(domainObject.coordinate().y(), is(yCoordinate));
        assertThat(domainObject.coordinate().z(), is(zCoordinate));
    }
    
    @Test
    public void testMap_givenDomainObject_shouldReturnEntity() {
        UUID id = UUID.randomUUID();
        Long eliteId = 123456L;
        String name = "System Name";
        Double xCoordinate = 123.45;
        Double yCoordinate = 678.90;
        Double zCoordinate = 234.56;
        Coordinate coordinate = new Coordinate(xCoordinate, yCoordinate, zCoordinate);
        System domainObject = new System(id, eliteId, name, coordinate);

        
        MybatisSystemEntity entity = underTest.map(domainObject);
        
        assertThat(entity.getId(), is(id));
        assertThat(entity.getEliteId(), is(eliteId));
        assertThat(entity.getName(), is(name));
        assertThat(entity.getXCoordinate(), is(xCoordinate));
        assertThat(entity.getYCoordinate(), is(yCoordinate));
        assertThat(entity.getZCoordinate(), is(zCoordinate));
    }
}