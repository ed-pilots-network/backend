package io.edpn.backend.trade.application.usecase;

import io.edpn.backend.trade.domain.model.CommodityMarketInfo;
import io.edpn.backend.trade.domain.repository.CommodityMarketInfoRepository;
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
public class DefaultFindCommodityMarketInfoUseCaseTest {

    @Mock
    private CommodityMarketInfoRepository commodityMarketInfoRepository;

    private DefaultFindCommodityMarketInfoUseCase underTest;

    @BeforeEach
    public void setup() {
        underTest = new DefaultFindCommodityMarketInfoUseCase(commodityMarketInfoRepository);
    }

    @Test
    public void testFindAll() {
        CommodityMarketInfo mockMarketInfo = mock(CommodityMarketInfo.class);
        List<CommodityMarketInfo> expectedMarketInfoList = Collections.singletonList(mockMarketInfo);

        when(commodityMarketInfoRepository.findAllCommodityMarketInfo()).thenReturn(expectedMarketInfoList);

        List<CommodityMarketInfo> actualMarketInfoList = underTest.findAll();

        verify(commodityMarketInfoRepository).findAllCommodityMarketInfo();
        verifyNoMoreInteractions(commodityMarketInfoRepository);

        assertThat(actualMarketInfoList, equalTo(expectedMarketInfoList));
    }
}
