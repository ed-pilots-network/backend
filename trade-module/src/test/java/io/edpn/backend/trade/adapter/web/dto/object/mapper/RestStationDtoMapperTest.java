package io.edpn.backend.trade.adapter.web.dto.object.mapper;

import io.edpn.backend.trade.adapter.web.dto.object.RestSystemDto;
import io.edpn.backend.trade.application.domain.LandingPadSize;
import io.edpn.backend.trade.application.domain.MarketDatum;
import io.edpn.backend.trade.application.domain.Station;
import io.edpn.backend.trade.application.domain.System;
import io.edpn.backend.trade.application.dto.web.object.StationDto;
import io.edpn.backend.trade.application.dto.web.object.mapper.StationDtoMapper;
import io.edpn.backend.trade.application.dto.web.object.mapper.SystemDtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestStationDtoMapperTest {
    
    @Mock
    private SystemDtoMapper systemDtoMapper;
    
    private StationDtoMapper underTest;
    
    @BeforeEach
    public void setUp(){
        underTest = new RestStationDtoMapper(systemDtoMapper);
    }
    
    @Test
    public void testMap_givenDomainObject_shouldReturnDto() {
        RestSystemDto mockSystemDto = mock(RestSystemDto.class);
        
        UUID id = UUID.randomUUID();
        Long marketId = 12345L;
        String name = "Station Name";
        Double arrivalDistance = 123.45;
        Boolean planetary = true;
        Boolean requireOdyssey = true;
        Boolean fleetCarrier = true;
        String maxLandingPadSize = "LARGE";
        LocalDateTime marketUpdatedAt = LocalDateTime.now();
        
        Station domainObject = new Station(
                id,
                marketId,
                name,
                arrivalDistance,
                mock(System.class),
                planetary,
                requireOdyssey,
                fleetCarrier,
                LandingPadSize.valueOf(maxLandingPadSize),
                marketUpdatedAt,
                List.of(mock(MarketDatum.class))
        );

        when(systemDtoMapper.map(domainObject.system())).thenReturn(mockSystemDto);
        
        StationDto dto = underTest.map(domainObject);
        
        assertThat(dto.marketId(), is(marketId));
        assertThat(dto.name(), is(name));
        assertThat(dto.arrivalDistance(), is(arrivalDistance));
        assertThat(dto.planetary(), is(planetary));
        assertThat(dto.requireOdyssey(), is(requireOdyssey));
        assertThat(dto.fleetCarrier(), is(fleetCarrier));
        assertThat(dto.maxLandingPadSize(), is(maxLandingPadSize));
        assertThat(dto.marketUpdatedAt(), is(marketUpdatedAt));
        
        verify(systemDtoMapper, times(1)).map(domainObject.system());
    }
}