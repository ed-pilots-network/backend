package io.edpn.backend.trade.infrastructure.persistence.repository;

import io.edpn.backend.trade.domain.model.Commodity;
import io.edpn.backend.trade.domain.model.CommodityMarketInfo;
import io.edpn.backend.trade.domain.model.Station;
import io.edpn.backend.trade.infrastructure.persistence.entity.CommodityEntity;
import io.edpn.backend.trade.infrastructure.persistence.entity.CommodityMarketInfoEntity;
import io.edpn.backend.trade.infrastructure.persistence.entity.StationEntity;
import io.edpn.backend.trade.infrastructure.persistence.mappers.entity.CommodityMarketInfoMapper;
import io.edpn.backend.trade.infrastructure.persistence.mappers.mybatis.CommodityMarketInfoEntityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MybatisCommodityMarketInfoRepositoryTest {

    @Mock
    private CommodityMarketInfoMapper commodityMarketInfoMapper;
    @Mock
    private CommodityMarketInfoEntityMapper commodityMarketInfoEntityMapper;

    private MybatisCommodityMarketInfoRepository repository;

    @BeforeEach
    public void setup() {
        repository = new MybatisCommodityMarketInfoRepository(commodityMarketInfoMapper, commodityMarketInfoEntityMapper);
    }

    @Test
    public void testFindCommodityMarketInfoByCommodityId() {
        // mock objects
        Commodity mockCommodity = mock(Commodity.class);
        CommodityEntity mockCommodityEntity = mock(CommodityEntity.class);
        Station mockHighestSellingToStation = mock(Station.class);
        StationEntity mockHighestSellingToStationEntity = mock(StationEntity.class);
        Station mockLowestBuyingFromStation = mock(Station.class);
        StationEntity mockLowestBuyingFromStationEntity = mock(StationEntity.class);

        UUID commodityId = UUID.randomUUID();
        CommodityMarketInfoEntity marketInfoEntity = CommodityMarketInfoEntity.builder()
                .commodity(mockCommodityEntity)
                .maxBuyPrice(9000.0)
                .minBuyPrice(1000.0)
                .avgBuyPrice(5000.0)
                .maxSellPrice(8000.0)
                .minSellPrice(2000.0)
                .avgSellPrice(6000.0)
                .minMeanPrice(4000.0)
                .maxMeanPrice(3000.0)
                .averageMeanPrice(7000.0)
                .totalStock(900L)
                .totalDemand(800L)
                .totalStations(250)
                .stationsWithBuyPrice(50)
                .stationsWithSellPrice(100)
                .stationsWithBuyPriceLowerThanAverage(600)
                .stationsWithSellPriceHigherThanAverage(700)
                .highestSellingToStation(mockHighestSellingToStationEntity)
                .lowestBuyingFromStation(mockLowestBuyingFromStationEntity)
                .build();

        CommodityMarketInfo marketInfo = CommodityMarketInfo.builder()
                .commodity(mockCommodity)
                .maxBuyPrice(9000.0)
                .minBuyPrice(1000.0)
                .avgBuyPrice(5000.0)
                .maxSellPrice(8000.0)
                .minSellPrice(2000.0)
                .avgSellPrice(6000.0)
                .minMeanPrice(4000.0)
                .maxMeanPrice(3000.0)
                .averageMeanPrice(7000.0)
                .totalStock(900L)
                .totalDemand(800L)
                .totalStations(250)
                .stationsWithBuyPrice(50)
                .stationsWithSellPrice(100)
                .stationsWithBuyPriceLowerThanAverage(600)
                .stationsWithSellPriceHigherThanAverage(700)
                .highestSellingToStation(mockHighestSellingToStation)
                .lowestBuyingFromStation(mockLowestBuyingFromStation)
                .build();

        when(commodityMarketInfoEntityMapper.findByCommodityId(commodityId)).thenReturn(Optional.of(marketInfoEntity));
        when(commodityMarketInfoMapper.map(marketInfoEntity)).thenReturn(marketInfo);

        Optional<CommodityMarketInfo> result = repository.findCommodityMarketInfoByCommodityId(commodityId);

        assertEquals(Optional.of(marketInfo), result);
    }

    @Test
    public void testFindAllCommodityMarketInfo() {
        // mock objects
        Commodity mockCommodity = mock(Commodity.class);
        CommodityEntity mockCommodityEntity = mock(CommodityEntity.class);
        Station mockHighestSellingToStation = mock(Station.class);
        StationEntity mockHighestSellingToStationEntity = mock(StationEntity.class);
        Station mockLowestBuyingFromStation = mock(Station.class);
        StationEntity mockLowestBuyingFromStationEntity = mock(StationEntity.class);

        CommodityMarketInfoEntity marketInfoEntity = CommodityMarketInfoEntity.builder()
                .commodity(mockCommodityEntity)
                .maxBuyPrice(9000.0)
                .minBuyPrice(1000.0)
                .avgBuyPrice(5000.0)
                .maxSellPrice(8000.0)
                .minSellPrice(2000.0)
                .avgSellPrice(6000.0)
                .minMeanPrice(4000.0)
                .maxMeanPrice(3000.0)
                .averageMeanPrice(7000.0)
                .totalStock(900L)
                .totalDemand(800L)
                .totalStations(250)
                .stationsWithBuyPrice(50)
                .stationsWithSellPrice(100)
                .stationsWithBuyPriceLowerThanAverage(600)
                .stationsWithSellPriceHigherThanAverage(700)
                .highestSellingToStation(mockHighestSellingToStationEntity)
                .lowestBuyingFromStation(mockLowestBuyingFromStationEntity)
                .build();

        CommodityMarketInfo marketInfo = CommodityMarketInfo.builder()
                .commodity(mockCommodity)
                .maxBuyPrice(9000.0)
                .minBuyPrice(1000.0)
                .avgBuyPrice(5000.0)
                .maxSellPrice(8000.0)
                .minSellPrice(2000.0)
                .avgSellPrice(6000.0)
                .minMeanPrice(4000.0)
                .maxMeanPrice(3000.0)
                .averageMeanPrice(7000.0)
                .totalStock(900L)
                .totalDemand(800L)
                .totalStations(250)
                .stationsWithBuyPrice(50)
                .stationsWithSellPrice(100)
                .stationsWithBuyPriceLowerThanAverage(600)
                .stationsWithSellPriceHigherThanAverage(700)
                .highestSellingToStation(mockHighestSellingToStation)
                .lowestBuyingFromStation(mockLowestBuyingFromStation)
                .build();

        when(commodityMarketInfoEntityMapper.findAll()).thenReturn(Collections.singletonList(marketInfoEntity));
        when(commodityMarketInfoMapper.map(marketInfoEntity)).thenReturn(marketInfo);

        List<CommodityMarketInfo> result = repository.findAllCommodityMarketInfo();

        assertEquals(Collections.singletonList(marketInfo), result);
    }
}
