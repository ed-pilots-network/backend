package io.edpn.backend.trade.application.service;

import io.edpn.backend.trade.application.domain.CommodityMarketInfo;
import io.edpn.backend.trade.application.dto.web.object.CommodityMarketInfoDto;
import io.edpn.backend.trade.application.dto.web.object.mapper.CommodityMarketInfoDtoMapper;
import io.edpn.backend.trade.application.port.incomming.commoditymarketinfo.GetFullCommodityMarketInfoUseCase;
import io.edpn.backend.trade.application.port.outgoing.commoditymarketinfo.GetFullCommodityMarketInfoPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetFullCommodityMarketInfoUseCaseTest {
    
    @Mock
    private GetFullCommodityMarketInfoPort commodityMarketInfoPort;
    
    @Mock
    private CommodityMarketInfoDtoMapper commodityMarketInfoDtoMapper;
    
    private GetFullCommodityMarketInfoUseCase underTest;
    
    @BeforeEach
    public void setUp(){
        underTest = new FindCommodityMarketInfoService(commodityMarketInfoPort, commodityMarketInfoDtoMapper);
    }
    
    @Test
    public void testFindFullCommodityMarketInfoList() {
        
        CommodityMarketInfo commodityMarketInfo1 = mock(CommodityMarketInfo.class);
        CommodityMarketInfo commodityMarketInfo2 = mock(CommodityMarketInfo.class);
        CommodityMarketInfo commodityMarketInfo3 = mock(CommodityMarketInfo.class);
        CommodityMarketInfoDto commodityMarketInfoDto1 = mock(CommodityMarketInfoDto.class);
        CommodityMarketInfoDto commodityMarketInfoDto2 = mock(CommodityMarketInfoDto.class);
        CommodityMarketInfoDto commodityMarketInfoDto3 = mock(CommodityMarketInfoDto.class);
        
        when(commodityMarketInfoPort.findAll()).thenReturn(List.of(commodityMarketInfo1,commodityMarketInfo2, commodityMarketInfo3));
        when(commodityMarketInfoDtoMapper.map(commodityMarketInfo1)).thenReturn(commodityMarketInfoDto1);
        when(commodityMarketInfoDtoMapper.map(commodityMarketInfo2)).thenReturn(commodityMarketInfoDto2);
        when(commodityMarketInfoDtoMapper.map(commodityMarketInfo3)).thenReturn(commodityMarketInfoDto3);
        
        List<CommodityMarketInfoDto> result = underTest.findAll();
        
        verify(commodityMarketInfoDtoMapper, times(1)).map(commodityMarketInfo1);
        verify(commodityMarketInfoDtoMapper, times(1)).map(commodityMarketInfo2);
        verify(commodityMarketInfoDtoMapper, times(1)).map(commodityMarketInfo3);
        assertThat(result, hasSize(3));
        assertThat(result, containsInAnyOrder(commodityMarketInfoDto1, commodityMarketInfoDto2, commodityMarketInfoDto3));

    }
    
}