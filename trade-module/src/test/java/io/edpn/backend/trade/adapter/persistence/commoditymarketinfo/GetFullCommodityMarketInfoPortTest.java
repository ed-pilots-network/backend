package io.edpn.backend.trade.adapter.persistence.commoditymarketinfo;

import io.edpn.backend.trade.adapter.persistence.CommodityMarketInfoRepository;
import io.edpn.backend.trade.adapter.persistence.entity.MybatisCommodityMarketInfoEntity;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.MybatisCommodityMarketInfoEntityMapper;
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
    private MybatisCommodityMarketInfoEntityMapper mybatisCommodityMarketInfoEntityMapper;

    private GetFullCommodityMarketInfoPort underTest;

    @BeforeEach
    public void setUp() {
        underTest = new CommodityMarketInfoRepository(mybatisCommodityMarketInfoRepository, mybatisCommodityMarketInfoEntityMapper);
    }

    @Test
    public void testFindAllCommodityMarketInfo() {
        // mock objects
        MybatisCommodityMarketInfoEntity marketInfoEntity = mock(MybatisCommodityMarketInfoEntity.class);
        CommodityMarketInfo marketInfo = mock(CommodityMarketInfo.class);

        when(mybatisCommodityMarketInfoRepository.findAll()).thenReturn(Collections.singletonList(marketInfoEntity));
        when(mybatisCommodityMarketInfoEntityMapper.map(marketInfoEntity)).thenReturn(marketInfo);

        List<CommodityMarketInfo> result = underTest.findAll();

        verify(mybatisCommodityMarketInfoRepository).findAll();
        verify(mybatisCommodityMarketInfoEntityMapper).map(marketInfoEntity);
        verifyNoMoreInteractions(mybatisCommodityMarketInfoRepository, mybatisCommodityMarketInfoEntityMapper);

        assertThat(result, equalTo(Collections.singletonList(marketInfo)));
    }
}
