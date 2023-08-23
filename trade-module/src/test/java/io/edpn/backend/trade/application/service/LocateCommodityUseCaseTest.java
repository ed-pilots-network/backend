package io.edpn.backend.trade.application.service;

import io.edpn.backend.trade.application.domain.LocateCommodity;
import io.edpn.backend.trade.application.domain.filter.LocateCommodityFilter;
import io.edpn.backend.trade.application.dto.web.filter.LocateCommodityFilterDto;
import io.edpn.backend.trade.application.dto.web.filter.mapper.LocateCommodityFilterDtoMapper;
import io.edpn.backend.trade.application.dto.web.object.LocateCommodityDto;
import io.edpn.backend.trade.application.dto.web.object.mapper.LocateCommodityDtoMapper;
import io.edpn.backend.trade.application.port.incomming.locatecommodity.LocateCommodityUseCase;
import io.edpn.backend.trade.application.port.outgoing.locatecommodity.LocateCommodityByFilterPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LocateCommodityUseCaseTest {
    
    @Mock
    private LocateCommodityByFilterPort locateCommodityByFilterPort;
    
    @Mock
    private LocateCommodityFilterDtoMapper locateCommodityFilterDtoMapper;
    
    @Mock
    private LocateCommodityDtoMapper locateCommodityDtoMapper;
    
    private LocateCommodityUseCase underTest;
    
    @BeforeEach
    public void setUp(){
        underTest = new LocateCommodityService(locateCommodityByFilterPort, locateCommodityFilterDtoMapper, locateCommodityDtoMapper);
    }
    
    @Test
    void shouldLocateCommoditiesNearby() {
        LocateCommodityFilterDto locateCommodityFilterDto = mock(LocateCommodityFilterDto.class);
        LocateCommodityFilter locateCommodityFilter = mock(LocateCommodityFilter.class);
        LocateCommodity locateCommodity = mock(LocateCommodity.class);
        LocateCommodityDto locateCommodityDto = mock(LocateCommodityDto.class);
        
        when(locateCommodityFilterDtoMapper.map(locateCommodityFilterDto)).thenReturn(locateCommodityFilter);
        when(locateCommodityByFilterPort.locateCommodityByFilter(locateCommodityFilter)).thenReturn(List.of(locateCommodity));
        when(locateCommodityDtoMapper.map(locateCommodity)).thenReturn(locateCommodityDto);
        
        List<LocateCommodityDto> responses = underTest.locateCommodityOrderByDistance(locateCommodityFilterDto);
        
        ArgumentCaptor<LocateCommodityFilter> argumentCaptor = ArgumentCaptor.forClass(LocateCommodityFilter.class);
        verify(locateCommodityByFilterPort).locateCommodityByFilter(argumentCaptor.capture());
        
        LocateCommodityFilter capturedLocateCommodity = argumentCaptor.getValue();
        
        assertThat(capturedLocateCommodity, equalTo(locateCommodityFilter));
        assertThat(responses, equalTo(List.of(locateCommodityDto)));
    }
    
}
