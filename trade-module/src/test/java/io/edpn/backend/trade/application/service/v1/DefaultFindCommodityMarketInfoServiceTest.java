package io.edpn.backend.trade.application.service.v1;

import io.edpn.backend.trade.application.dto.v1.CommodityMarketInfoResponse;
import io.edpn.backend.trade.application.mappers.v1.CommodityMarketInfoResponseMapper;
import io.edpn.backend.trade.domain.model.CommodityMarketInfo;
import io.edpn.backend.trade.domain.usecase.FindCommodityMarketInfoUseCase;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DefaultFindCommodityMarketInfoServiceTest {

    @Mock
    private FindCommodityMarketInfoUseCase findCommodityMarketInfoUseCase;
    @Mock
    private CommodityMarketInfoResponseMapper commodityMarketInfoResponseMapper;

    private DefaultFindCommodityMarketInfoService underTest;

    @BeforeEach
    void setUp() {
        underTest = new DefaultFindCommodityMarketInfoService(findCommodityMarketInfoUseCase, commodityMarketInfoResponseMapper);
    }

    @Test
    void shouldGetCommodityMarketInfo() {
        CommodityMarketInfo commodityMarketInfo = mock(CommodityMarketInfo.class);
        CommodityMarketInfoResponse commodityMarketInfoResponse = mock(CommodityMarketInfoResponse.class);

        when(findCommodityMarketInfoUseCase.findAll()).thenReturn(List.of(commodityMarketInfo));
        when(commodityMarketInfoResponseMapper.map(commodityMarketInfo)).thenReturn(commodityMarketInfoResponse);

        List<CommodityMarketInfoResponse> responses = underTest.getCommodityMarketInfo();

        assertThat(responses, equalTo(List.of(commodityMarketInfoResponse)));
    }
}
