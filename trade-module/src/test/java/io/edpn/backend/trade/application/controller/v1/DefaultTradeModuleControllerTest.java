package io.edpn.backend.trade.application.controller.v1;

import io.edpn.backend.trade.application.dto.v1.CommodityMarketInfoResponse;
import io.edpn.backend.trade.application.dto.v1.FindCommodityRequest;
import io.edpn.backend.trade.application.dto.v1.FindCommodityResponse;
import io.edpn.backend.trade.application.dto.v1.LocateCommodityRequest;
import io.edpn.backend.trade.application.dto.v1.LocateCommodityResponse;
import io.edpn.backend.trade.application.mappers.v1.CommodityMarketInfoResponseMapper;
import io.edpn.backend.trade.application.mappers.v1.FindCommodityDTOMapper;
import io.edpn.backend.trade.application.mappers.v1.LocateCommodityDTOMapper;
import io.edpn.backend.trade.domain.filter.v1.FindCommodityFilter;
import io.edpn.backend.trade.domain.filter.v1.LocateCommodityFilter;
import io.edpn.backend.trade.domain.model.CommodityMarketInfo;
import io.edpn.backend.trade.domain.model.LocateCommodity;
import io.edpn.backend.trade.domain.model.ValidatedCommodity;
import io.edpn.backend.trade.domain.usecase.FindCommodityMarketInfoUseCase;
import io.edpn.backend.trade.domain.usecase.FindCommodityUseCase;
import io.edpn.backend.trade.domain.usecase.LocateCommodityUseCase;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DefaultTradeModuleControllerTest {

    @Mock
    private FindCommodityMarketInfoUseCase findCommodityMarketInfoUseCase;
    @Mock
    private LocateCommodityUseCase locateCommodityUseCase;
    @Mock
    private FindCommodityUseCase findCommodityUseCase;

    @Mock
    private CommodityMarketInfoResponseMapper commodityMarketInfoResponseMapper;
    @Mock
    private LocateCommodityDTOMapper locateCommodityDTOMapper;
    @Mock
    private FindCommodityDTOMapper findCommodityDTOMapper;
    private DefaultTradeModuleController underTest;

    @BeforeEach
    void setUp() {
        underTest = new DefaultTradeModuleController(findCommodityMarketInfoUseCase, locateCommodityUseCase, findCommodityUseCase, commodityMarketInfoResponseMapper, locateCommodityDTOMapper, findCommodityDTOMapper);
    }

    @Test
    public void getBestCommodityPriceTest() {
        //given
        CommodityMarketInfo commodityMarketInfo = mock(CommodityMarketInfo.class);
        List<CommodityMarketInfo> commodityMarketInfoList = List.of(commodityMarketInfo);
        CommodityMarketInfoResponse commodityMarketInfoResponse = mock(CommodityMarketInfoResponse.class);
        List<CommodityMarketInfoResponse> commodityMarketInfoResponseList = List.of(commodityMarketInfoResponse);

        when(findCommodityMarketInfoUseCase.findAll()).thenReturn(commodityMarketInfoList);
        when(commodityMarketInfoResponseMapper.map(commodityMarketInfo)).thenReturn(commodityMarketInfoResponse);

        //when
        List<CommodityMarketInfoResponse> result = underTest.getBestCommodityPrice();

        //then
        assertThat(result, is(commodityMarketInfoResponseList));
    }

    @Test
    public void getBestCommodityPriceEmptyResultTest() {
        //given
        List<CommodityMarketInfo> commodityMarketInfoList = Collections.emptyList();

        when(findCommodityMarketInfoUseCase.findAll()).thenReturn(commodityMarketInfoList);

        //when
        List<CommodityMarketInfoResponse> result = underTest.getBestCommodityPrice();

        //then
        assertThat(result, is(Matchers.empty()));
    }

    @Test
    public void locateCommodityWithFiltersTest() {
        // given
        LocateCommodityRequest request = mock(LocateCommodityRequest.class);
        LocateCommodityFilter filter = mock(LocateCommodityFilter.class);
        LocateCommodity locateCommodity = mock(LocateCommodity.class);
        List<LocateCommodity> locateCommodities = List.of(locateCommodity);
        LocateCommodityResponse response = mock(LocateCommodityResponse.class);
        List<LocateCommodityResponse> responses = List.of(response);

        when(locateCommodityDTOMapper.map(request)).thenReturn(filter);
        when(locateCommodityUseCase.locateCommoditiesOrderByDistance(filter)).thenReturn(locateCommodities);
        when(locateCommodityDTOMapper.map(locateCommodity)).thenReturn(response);

        // when
        List<LocateCommodityResponse> result = underTest.locateCommodityWithFilters(request);

        // then
        assertThat(result, is(responses));
    }

    @Test
    public void locateCommodityWithFiltersEmptyResultTest() {
        // given
        LocateCommodityRequest request = mock(LocateCommodityRequest.class);
        LocateCommodityFilter filter = mock(LocateCommodityFilter.class);

        when(locateCommodityDTOMapper.map(request)).thenReturn(filter);
        when(locateCommodityUseCase.locateCommoditiesOrderByDistance(filter)).thenReturn(Collections.emptyList());

        // when
        List<LocateCommodityResponse> result = underTest.locateCommodityWithFilters(request);

        // then
        assertThat(result, is(Matchers.empty()));
    }

    @Test
    public void findValidatedCommodityByNameTest() {
        // given
        String displayName = "gold";
        ValidatedCommodity commodity = mock(ValidatedCommodity.class);
        FindCommodityResponse response = mock(FindCommodityResponse.class);

        when(findCommodityUseCase.findByName(displayName)).thenReturn(Optional.of(commodity));
        when(findCommodityDTOMapper.map(commodity)).thenReturn(response);

        // when
        Optional<FindCommodityResponse> result = underTest.findValidatedCommodityByName(displayName);

        // then
        assertThat(result, is(Optional.of(response)));
    }

    @Test
    public void findValidatedCommodityByNameEmptyResultTest() {
        // given
        String displayName = "gold";

        when(findCommodityUseCase.findByName(displayName)).thenReturn(Optional.empty());

        // when
        Optional<FindCommodityResponse> result = underTest.findValidatedCommodityByName(displayName);

        // then
        assertThat(result, is(Optional.empty()));
    }

    @Test
    public void findAllValidatedCommoditiesTest() {
        // given
        ValidatedCommodity commodity = mock(ValidatedCommodity.class);
        List<ValidatedCommodity> commodities = List.of(commodity);
        FindCommodityResponse response = mock(FindCommodityResponse.class);
        List<FindCommodityResponse> responses = List.of(response);

        when(findCommodityUseCase.findAll()).thenReturn(commodities);
        when(findCommodityDTOMapper.map(commodity)).thenReturn(response);

        // when
        List<FindCommodityResponse> result = underTest.findAllValidatedCommodities();

        // then
        assertThat(result, is(responses));
    }

    @Test
    public void findAllValidatedCommoditiesEmptyResultTest() {
        // given
        when(findCommodityUseCase.findAll()).thenReturn(Collections.emptyList());

        // when
        List<FindCommodityResponse> result = underTest.findAllValidatedCommodities();

        // then
        assertThat(result, is(Matchers.empty()));
    }

    @Test
    public void findValidatedCommodityByFilterTest() {
        // given
        FindCommodityRequest request = mock(FindCommodityRequest.class);
        FindCommodityFilter commodityFilter = mock(FindCommodityFilter.class);
        ValidatedCommodity commodity = mock(ValidatedCommodity.class);
        List<ValidatedCommodity> commodities = List.of(commodity);
        FindCommodityResponse response = mock(FindCommodityResponse.class);
        List<FindCommodityResponse> responses = List.of(response);

        when(findCommodityDTOMapper.map(request)).thenReturn(commodityFilter);
        when(findCommodityUseCase.findByFilter(commodityFilter)).thenReturn(commodities);
        when(findCommodityDTOMapper.map(commodity)).thenReturn(response);

        // when
        List<FindCommodityResponse> result = underTest.findValidatedCommodityByFilter(request);

        // then
        assertThat(result, is(responses));
    }

    @Test
    public void findValidatedCommodityByFilterEmptyResultTest() {
        // given
        FindCommodityRequest request = mock(FindCommodityRequest.class);

        when(findCommodityDTOMapper.map(request)).thenReturn(null);

        // when
        List<FindCommodityResponse> result = underTest.findValidatedCommodityByFilter(request);

        // then
        assertThat(result, is(Matchers.empty()));
    }
}
