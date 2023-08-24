package io.edpn.backend.trade.application.service;

import io.edpn.backend.trade.application.domain.ValidatedCommodity;
import io.edpn.backend.trade.application.dto.web.filter.mapper.FindCommodityFilterDtoMapper;
import io.edpn.backend.trade.application.dto.web.object.ValidatedCommodityDto;
import io.edpn.backend.trade.application.dto.web.object.mapper.ValidatedCommodityDtoMapper;
import io.edpn.backend.trade.application.port.incomming.validatedcommodity.FindValidatedCommodityByNameUseCase;
import io.edpn.backend.trade.application.port.outgoing.validatedcommodity.LoadAllValidatedCommodityPort;
import io.edpn.backend.trade.application.port.outgoing.validatedcommodity.LoadValidatedCommodityByFilterPort;
import io.edpn.backend.trade.application.port.outgoing.validatedcommodity.LoadValidatedCommodityByNamePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(MockitoExtension.class)
public class FindValidatedCommodityByNameUseCaseTest {
    
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
    
    private FindValidatedCommodityByNameUseCase underTest;
    
    @BeforeEach
    public void setUp(){
        underTest = new FindCommodityService(loadAllValidatedCommodityPort, loadValidatedCommodityByNamePort, loadValidatedCommodityByFilterPort, validatedCommodityDTOMapper, findCommodityFilterDtoMapper);
    }
    
    @Test
    public void testValidatedCommodityFoundByDisplayName() {
        ValidatedCommodity mockValidatedCommodity = mock(ValidatedCommodity.class);
        ValidatedCommodityDto mockValidatedCommodityDto = mock(ValidatedCommodityDto.class);
        String displayName = "Commodity Name";
        
        when(loadValidatedCommodityByNamePort.loadByName(displayName)).thenReturn(Optional.ofNullable(mockValidatedCommodity));
        when(validatedCommodityDTOMapper.map(mockValidatedCommodity)).thenReturn(mockValidatedCommodityDto);
    
        Optional<ValidatedCommodityDto> response = underTest.findByName(displayName);
        
        assertThat(response, equalTo(Optional.ofNullable(mockValidatedCommodityDto)));
    }
}
