package io.edpn.backend.trade.adapter.web.dto.object.mapper;

import io.edpn.backend.trade.application.domain.System;
import io.edpn.backend.trade.application.dto.web.object.SystemDto;
import io.edpn.backend.trade.application.dto.web.object.mapper.SystemDtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(MockitoExtension.class)
class RestSystemDtoMapperTest {
    
    private SystemDtoMapper underTest;
    
    @BeforeEach
    public void setUp(){
        underTest = new RestSystemDtoMapper();
    }
    
    @Test
    public void testMap_givenDomainObject_shouldReturnDto() {
        Long eliteId = 123456L;
        String name = "System Name";
        Double xCoordinate = 123.45;
        Double yCoordinate = 678.90;
        Double zCoordinate = 234.56;
        System domainObject = System.builder()
                .eliteId(eliteId)
                .name(name)
                .xCoordinate(xCoordinate)
                .yCoordinate(yCoordinate)
                .zCoordinate(zCoordinate)
                .build();
        
        SystemDto dto = underTest.map(domainObject);
        
        assertThat(dto.eliteId(), is(eliteId));
        assertThat(dto.name(), is(name));
        assertThat(dto.xCoordinate(), is(xCoordinate));
        assertThat(dto.yCoordinate(), is(yCoordinate));
        assertThat(dto.zCoordinate(), is(zCoordinate));
    }

}