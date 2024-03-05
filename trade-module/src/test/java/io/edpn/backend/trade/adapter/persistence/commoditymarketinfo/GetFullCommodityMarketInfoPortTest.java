package io.edpn.backend.trade.adapter.persistence.commoditymarketinfo;

import io.edpn.backend.trade.adapter.persistence.CommodityMarketInfoRepository;
import io.edpn.backend.trade.adapter.persistence.entity.CommodityMarketInfoEntity;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.CommodityMarketInfoEntityMapper;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisCommodityMarketInfoRepository;
import io.edpn.backend.trade.application.domain.CommodityMarketInfo;
import io.edpn.backend.trade.application.port.outgoing.commoditymarketinfo.GetFullCommodityMarketInfoPort;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetFullCommodityMarketInfoPortTest {

    @Mock
    private MybatisCommodityMarketInfoRepository mybatisCommodityMarketInfoRepository;

    @Mock
    private CommodityMarketInfoEntityMapper commodityMarketInfoEntityMapper;

    private GetFullCommodityMarketInfoPort underTest;

    @BeforeEach
    public void setUp() {
        underTest = new CommodityMarketInfoRepository(mybatisCommodityMarketInfoRepository, commodityMarketInfoEntityMapper);
    }

    @Test
    public void testFindAllCommodityMarketInfo() {
        // mock objects
        CommodityMarketInfoEntity marketInfoEntity = mock(CommodityMarketInfoEntity.class);
        CommodityMarketInfo marketInfo = mock(CommodityMarketInfo.class);

        when(mybatisCommodityMarketInfoRepository.findAll()).thenReturn(Collections.singletonList(marketInfoEntity));
        when(commodityMarketInfoEntityMapper.map(marketInfoEntity)).thenReturn(marketInfo);

        List<CommodityMarketInfo> result = underTest.findAll();

        verify(mybatisCommodityMarketInfoRepository).findAll();
        verify(commodityMarketInfoEntityMapper).map(marketInfoEntity);
        verifyNoMoreInteractions(mybatisCommodityMarketInfoRepository, commodityMarketInfoEntityMapper);

        assertThat(result, equalTo(Collections.singletonList(marketInfo)));
    }
}
