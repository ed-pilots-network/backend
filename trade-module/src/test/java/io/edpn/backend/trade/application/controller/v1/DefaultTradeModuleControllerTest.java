package io.edpn.backend.trade.application.controller.v1;

import io.edpn.backend.trade.application.dto.v1.CommodityMarketInfoResponse;
import io.edpn.backend.trade.application.dto.v1.LocateCommodityRequest;
import io.edpn.backend.trade.application.dto.v1.LocateCommodityResponse;
import io.edpn.backend.trade.domain.service.v1.FindCommodityMarketInfoService;
import io.edpn.backend.trade.domain.service.v1.LocateCommodityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DefaultTradeModuleControllerTest {

    @Mock
    private FindCommodityMarketInfoService findCommodityMarketInfoService;
    @Mock
    private LocateCommodityService locateCommodityService;

    private DefaultTradeModuleController underTest;

    @BeforeEach
    void setUp() {
        underTest = new DefaultTradeModuleController(findCommodityMarketInfoService, locateCommodityService);
    }

    @Test
    void shouldGetBestCommodityPrice() {
        CommodityMarketInfoResponse response = CommodityMarketInfoResponse.builder().build();
        when(findCommodityMarketInfoService.getCommodityMarketInfo()).thenReturn(List.of(response));

        List<CommodityMarketInfoResponse> result = underTest.getBestCommodityPrice();

        assertThat(result, equalTo(List.of(response)));
    }

    @Test
    void shouldLocateByCommodityWithFilters() {
        LocateCommodityRequest request = mock(LocateCommodityRequest.class);
        LocateCommodityResponse response = LocateCommodityResponse.builder().build();
        when(locateCommodityService.locateCommoditiesOrderByDistance(request)).thenReturn(List.of(response));

        List<LocateCommodityResponse> result = underTest.locateCommodityWithFilters(request);

        assertThat(result, equalTo(List.of(response)));
    }
}
