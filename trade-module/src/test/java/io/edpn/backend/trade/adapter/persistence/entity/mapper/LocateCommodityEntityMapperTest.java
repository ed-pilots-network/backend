package io.edpn.backend.trade.adapter.persistence.entity.mapper;

import io.edpn.backend.trade.adapter.persistence.entity.LocateCommodityEntity;
import io.edpn.backend.trade.adapter.persistence.entity.StationEntity;
import io.edpn.backend.trade.adapter.persistence.entity.ValidatedCommodityEntity;
import io.edpn.backend.trade.application.domain.LocateCommodity;
import io.edpn.backend.trade.application.domain.Station;
import io.edpn.backend.trade.application.domain.ValidatedCommodity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LocateCommodityEntityMapperTest {

    @Mock
    private ValidatedCommodityEntityMapper validatedCommodityEntityMapper;

    @Mock
    private StationEntityMapper stationEntityMapper;

    private LocateCommodityEntityMapper underTest;

    @BeforeEach
    public void setUp() {
        underTest = new LocateCommodityEntityMapper(validatedCommodityEntityMapper, stationEntityMapper);
    }

    @Test
    public void testMap_givenEntity_shouldReturnDomainObject() {
        // create mock objects
        ValidatedCommodity mockCommodity = mock(ValidatedCommodity.class);
        Station mockStation = mock(Station.class);
        LocalDateTime pricesUpdatedAt = LocalDateTime.now();

        // Setup the LocateCommodity Entity with test data
        LocateCommodityEntity entity = LocateCommodityEntity.builder()
                .priceUpdatedAt(pricesUpdatedAt)
                .validatedCommodity(mock(ValidatedCommodityEntity.class))
                .station(mock(StationEntity.class))
                .supply(100L)
                .demand(200L)
                .buyPrice(1234L)
                .sellPrice(4321L)
                .distance(63.5)
                .build();

        when(validatedCommodityEntityMapper.map(entity.getValidatedCommodity())).thenReturn(mockCommodity);
        when(stationEntityMapper.map(entity.getStation())).thenReturn(mockStation);

        // Map the entity to a CommodityMarketInfo object
        LocateCommodity result = underTest.map(entity);

        // Verify that the result matches the expected values
        assertThat(result.priceUpdatedAt(), is(pricesUpdatedAt));
        assertThat(result.validatedCommodity(), is(mockCommodity));
        assertThat(result.station(), is(mockStation));
        assertThat(result.supply(), is(100L));
        assertThat(result.demand(), is(200L));
        assertThat(result.buyPrice(), is(1234L));
        assertThat(result.sellPrice(), is(4321L));
        assertThat(result.distance(), is(63.5));

        verify(validatedCommodityEntityMapper, times(1)).map(entity.getValidatedCommodity());
        verify(stationEntityMapper, times(1)).map(entity.getStation());


    }

    @Test
    public void testMap_givenDomainObject_shouldReturnEntity() {
        // create mock objects
        ValidatedCommodityEntity mockCommodityEntity = mock(ValidatedCommodityEntity.class);
        StationEntity mockStationEntity = mock(StationEntity.class);
        LocalDateTime pricesUpdatedAt = LocalDateTime.now();

        // Setup the LocateCommodity Object with test data
        LocateCommodity domainObject = new LocateCommodity(
                pricesUpdatedAt,
                mock(ValidatedCommodity.class),
                mock(Station.class),
                100L,
                200L,
                1234L,
                4321L,
                63.5
        );

        when(validatedCommodityEntityMapper.map(domainObject.validatedCommodity())).thenReturn(mockCommodityEntity);
        when(stationEntityMapper.map(domainObject.station())).thenReturn(mockStationEntity);

        // Map the entity to a CommodityMarketInfo object
        LocateCommodityEntity result = underTest.map(domainObject);

        // Verify that the result matches the expected values
        assertThat(result.getPriceUpdatedAt(), is(pricesUpdatedAt));
        assertThat(result.getValidatedCommodity(), is(mockCommodityEntity));
        assertThat(result.getStation(), is(mockStationEntity));
        assertThat(result.getSupply(), is(100L));
        assertThat(result.getDemand(), is(200L));
        assertThat(result.getBuyPrice(), is(1234L));
        assertThat(result.getSellPrice(), is(4321L));
        assertThat(result.getDistance(), is(63.5));

        verify(validatedCommodityEntityMapper, times(1)).map(domainObject.validatedCommodity());
        verify(stationEntityMapper, times(1)).map(domainObject.station());

    }

}