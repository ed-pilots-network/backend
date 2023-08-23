package io.edpn.backend.trade.application.service;

import io.edpn.backend.trade.application.domain.ValidatedCommodity;
import io.edpn.backend.trade.application.dto.web.filter.mapper.FindCommodityFilterDtoMapper;
import io.edpn.backend.trade.application.dto.web.object.ValidatedCommodityDto;
import io.edpn.backend.trade.application.dto.web.object.mapper.ValidatedCommodityDtoMapper;
import io.edpn.backend.trade.application.port.incomming.validatedcommodity.FindAllValidatedCommodityUseCase;
import io.edpn.backend.trade.application.port.outgoing.validatedcommodity.LoadAllValidatedCommodityPort;
import io.edpn.backend.trade.application.port.outgoing.validatedcommodity.LoadValidatedCommodityByFilterPort;
import io.edpn.backend.trade.application.port.outgoing.validatedcommodity.LoadValidatedCommodityByNamePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;

@ExtendWith(MockitoExtension.class)
class FindAllValidatedCommodityUseCaseTest {
    
    @Mock
    private LoadAllValidatedCommodityPort loadAllValidatedCommodityPort;
    
    @Mock
    private LoadValidatedCommodityByNamePort loadValidatedCommodityByNamePort;
    
    @Mock
    private LoadValidatedCommodityByFilterPort loadValidatedCommodityByFilterPort;
    
    @Mock
    private ValidatedCommodityDtoMapper validatedCommodityDTOMapper;
    
    @Mock
    private FindCommodityFilterDtoMapper findCommodityFilterDtoMapper;
    
    private FindAllValidatedCommodityUseCase underTest;
    
    @BeforeEach
    public void setUp(){
        underTest = new FindCommodityService(loadAllValidatedCommodityPort, loadValidatedCommodityByNamePort, loadValidatedCommodityByFilterPort, validatedCommodityDTOMapper, findCommodityFilterDtoMapper);
    }
    
    @Test
    public void testFindAllValidateCommodities() {
        ValidatedCommodity validatedCommodity1 = mock(ValidatedCommodity.class);
        ValidatedCommodity validatedCommodity2 = mock(ValidatedCommodity.class);
        ValidatedCommodity validatedCommodity3 = mock(ValidatedCommodity.class);
        ValidatedCommodityDto validatedCommodityDto1 = mock(ValidatedCommodityDto.class);
        ValidatedCommodityDto validatedCommodityDto2 = mock(ValidatedCommodityDto.class);
        ValidatedCommodityDto validatedCommodityDto3 = mock(ValidatedCommodityDto.class);
        
        when(loadAllValidatedCommodityPort.loadAll()).thenReturn(List.of(validatedCommodity1, validatedCommodity2, validatedCommodity3));
        when(validatedCommodityDTOMapper.map(validatedCommodity1)).thenReturn(validatedCommodityDto1);
        when(validatedCommodityDTOMapper.map(validatedCommodity2)).thenReturn(validatedCommodityDto2);
        when(validatedCommodityDTOMapper.map(validatedCommodity3)).thenReturn(validatedCommodityDto3);
        
        List<ValidatedCommodityDto> result = underTest.findAll();
        
        verify(validatedCommodityDTOMapper, times(1)).map(validatedCommodity1);
        verify(validatedCommodityDTOMapper, times(1)).map(validatedCommodity2);
        verify(validatedCommodityDTOMapper, times(1)).map(validatedCommodity3);
        assertThat(result, hasSize(3));
        assertThat(result, containsInAnyOrder(validatedCommodityDto1, validatedCommodityDto2, validatedCommodityDto3));
        
    }

}