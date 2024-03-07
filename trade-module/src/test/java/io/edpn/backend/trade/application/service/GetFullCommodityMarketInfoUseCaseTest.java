package io.edpn.backend.trade.application.service;

import io.edpn.backend.trade.application.domain.CommodityMarketInfo;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetFullCommodityMarketInfoUseCaseTest {

    @Mock
    private GetFullCommodityMarketInfoPort commodityMarketInfoPort;

    private GetFullCommodityMarketInfoUseCase underTest;

    @BeforeEach
    public void setUp() {
        underTest = new FindCommodityMarketInfoService(commodityMarketInfoPort);
    }

    @Test
    public void testFindFullCommodityMarketInfoList() {

        CommodityMarketInfo commodityMarketInfo1 = mock(CommodityMarketInfo.class);
        CommodityMarketInfo commodityMarketInfo2 = mock(CommodityMarketInfo.class);
        CommodityMarketInfo commodityMarketInfo3 = mock(CommodityMarketInfo.class);

        when(commodityMarketInfoPort.findAll()).thenReturn(List.of(commodityMarketInfo1, commodityMarketInfo2, commodityMarketInfo3));

        List<CommodityMarketInfo> result = underTest.findAll();

        assertThat(result, hasSize(3));
        assertThat(result, containsInAnyOrder(commodityMarketInfo1, commodityMarketInfo2, commodityMarketInfo3));

    }

}