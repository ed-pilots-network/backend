package io.edpn.backend.trade.application.service;

import io.edpn.backend.trade.application.domain.ValidatedCommodity;
import io.edpn.backend.trade.application.domain.filter.FindCommodityFilter;
import io.edpn.backend.trade.application.dto.web.filter.FindCommodityFilterDto;
import io.edpn.backend.trade.application.dto.web.filter.mapper.FindCommodityFilterDtoMapper;
import io.edpn.backend.trade.application.dto.web.object.ValidatedCommodityDto;
import io.edpn.backend.trade.application.dto.web.object.mapper.ValidatedCommodityDtoMapper;
import io.edpn.backend.trade.application.port.incomming.validatedcommodity.FindValidatedCommodityByFilterUseCase;
import io.edpn.backend.trade.application.port.outgoing.validatedcommodity.LoadAllValidatedCommodityPort;
import io.edpn.backend.trade.application.port.outgoing.validatedcommodity.LoadValidatedCommodityByFilterPort;
import io.edpn.backend.trade.application.port.outgoing.validatedcommodity.LoadValidatedCommodityByNamePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindValidatedCommodityByFilterUseCaseTest {
    
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
    
    private FindValidatedCommodityByFilterUseCase underTest;
    
    @BeforeEach
    public void setUp(){
        underTest = new FindCommodityService(loadAllValidatedCommodityPort, loadValidatedCommodityByNamePort, loadValidatedCommodityByFilterPort, validatedCommodityDTOMapper, findCommodityFilterDtoMapper);
    }
    
    @Test
    public void testValidatedCommoditiesFoundByFilter() {
        
        FindCommodityFilterDto mockFindCommodityFilterDto = mock(FindCommodityFilterDto.class);
        FindCommodityFilter mockFindCommodityFilter = mock(FindCommodityFilter.class);
        ValidatedCommodity mockValidatedCommodity = mock(ValidatedCommodity.class);
        ValidatedCommodityDto mockValidatedCommodityDto = mock(ValidatedCommodityDto.class);
        List<ValidatedCommodityDto> findCommodityResponseList = Collections.singletonList(mockValidatedCommodityDto);
        
        when(findCommodityFilterDtoMapper.map(mockFindCommodityFilterDto)).thenReturn(mockFindCommodityFilter);
        when(loadValidatedCommodityByFilterPort.loadByFilter(mockFindCommodityFilter)).thenReturn(List.of(mockValidatedCommodity));
        when(validatedCommodityDTOMapper.map(mockValidatedCommodity)).thenReturn(mockValidatedCommodityDto);
        
        List<ValidatedCommodityDto> responseList = underTest.findByFilter(mockFindCommodityFilterDto);

        verify(findCommodityFilterDtoMapper, times(1)).map(mockFindCommodityFilterDto);
        verify(validatedCommodityDTOMapper, times(1)).map(mockValidatedCommodity);
        
        assertThat(responseList, equalTo(findCommodityResponseList));
    }
}
