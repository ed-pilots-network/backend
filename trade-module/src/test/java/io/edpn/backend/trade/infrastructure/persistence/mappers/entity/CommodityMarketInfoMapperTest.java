package io.edpn.backend.trade.infrastructure.persistence.mappers.entity;

import io.edpn.backend.trade.domain.model.CommodityMarketInfo;
import io.edpn.backend.trade.domain.model.Station;
import io.edpn.backend.trade.domain.model.ValidatedCommodity;
import io.edpn.backend.trade.infrastructure.persistence.entity.CommodityMarketInfoEntity;
import io.edpn.backend.trade.infrastructure.persistence.entity.StationEntity;
import io.edpn.backend.trade.infrastructure.persistence.entity.ValidatedCommodityEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommodityMarketInfoMapperTest {
    @Mock
    private ValidatedCommodityMapper validatedCommodityMapper;

    @Mock
    private StationMapper stationMapper;

    private CommodityMarketInfoMapper underTest;

    @BeforeEach
    public void setup() {
        underTest = new CommodityMarketInfoMapper(validatedCommodityMapper, stationMapper);
    }

    @Test
    public void shouldMapCommodityMarketInfoEntityToCommodityMarketInfo() {
        // Setup mock Commodity and Station objects
        ValidatedCommodity mockCommodity = mock(ValidatedCommodity.class);
        Station mockHighestSellingStation = mock(Station.class);
        Station mockLowestBuyingStation = mock(Station.class);

        // Setup the CommodityMarketInfoEntity with test data
        CommodityMarketInfoEntity entity = CommodityMarketInfoEntity.builder()
                .validatedCommodity(mock(ValidatedCommodityEntity.class))
                .maxBuyPrice(100.0)
                .minBuyPrice(50.0)
                .avgBuyPrice(75.0)
                .maxSellPrice(200.0)
                .minSellPrice(100.0)
                .avgSellPrice(150.0)
                .minMeanPrice(60.0)
                .maxMeanPrice(180.0)
                .averageMeanPrice(120.0)
                .totalStock(1000L)
                .totalDemand(500L)
                .totalStations(10)
                .stationsWithBuyPrice(5)
                .stationsWithSellPrice(4)
                .stationsWithBuyPriceLowerThanAverage(2)
                .stationsWithSellPriceHigherThanAverage(1)
                .highestSellingToStation(mock(StationEntity.class))
                .lowestBuyingFromStation(mock(StationEntity.class))
                .build();

        when(validatedCommodityMapper.map(entity.getValidatedCommodity())).thenReturn(mockCommodity);
        when(stationMapper.map(entity.getHighestSellingToStation())).thenReturn(mockHighestSellingStation);
        when(stationMapper.map(entity.getLowestBuyingFromStation())).thenReturn(mockLowestBuyingStation);

        // Map the entity to a CommodityMarketInfo object
        CommodityMarketInfo result = underTest.map(entity);

        // Verify that the result matches the expected values
        assertThat(result.getValidatedCommodity(), is(mockCommodity));
        assertThat(result.getMaxBuyPrice(), is(100.0));
        assertThat(result.getMinBuyPrice(), is(50.0));
        assertThat(result.getAvgBuyPrice(), is(75.0));
        assertThat(result.getMaxSellPrice(), is(200.0));
        assertThat(result.getMinSellPrice(), is(100.0));
        assertThat(result.getAvgSellPrice(), is(150.0));
        assertThat(result.getMinMeanPrice(), is(60.0));
        assertThat(result.getMaxMeanPrice(), is(180.0));
        assertThat(result.getAverageMeanPrice(), is(120.0));
        assertThat(result.getTotalStock(), is(1000L));
        assertThat(result.getTotalDemand(), is(500L));
        assertThat(result.getTotalStations(), is(10));
        assertThat(result.getStationsWithBuyPrice(), is(5));
        assertThat(result.getStationsWithSellPrice(), is(4));
        assertThat(result.getStationsWithBuyPriceLowerThanAverage(), is(2));
        assertThat(result.getStationsWithSellPriceHigherThanAverage(), is(1));
        assertThat(result.getHighestSellingToStation(), is(mockHighestSellingStation));
        assertThat(result.getLowestBuyingFromStation(), is(mockLowestBuyingStation));

        verify(validatedCommodityMapper, times(1)).map(entity.getValidatedCommodity());
        verify(stationMapper, times(1)).map(entity.getHighestSellingToStation());
        verify(stationMapper, times(1)).map(entity.getLowestBuyingFromStation());
    }
}
