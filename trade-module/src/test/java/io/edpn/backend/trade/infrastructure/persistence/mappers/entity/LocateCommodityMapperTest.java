package io.edpn.backend.trade.infrastructure.persistence.mappers.entity;

import io.edpn.backend.trade.domain.model.Commodity;
import io.edpn.backend.trade.domain.model.LocateCommodity;
import io.edpn.backend.trade.domain.model.Station;
import io.edpn.backend.trade.domain.model.System;
import io.edpn.backend.trade.infrastructure.persistence.entity.CommodityEntity;
import io.edpn.backend.trade.infrastructure.persistence.entity.LocateCommodityEntity;
import io.edpn.backend.trade.infrastructure.persistence.entity.StationEntity;
import io.edpn.backend.trade.infrastructure.persistence.entity.SystemEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
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
public class LocateCommodityMapperTest {

    @Mock
    private CommodityMapper commodityMapper;

    @Mock
    private SystemMapper systemMapper;

    @Mock
    private StationMapper stationMapper;

    @InjectMocks
    private LocateCommodityMapper underTest;

    @BeforeEach
    public void setUp() {
        underTest = new LocateCommodityMapper(commodityMapper, systemMapper, stationMapper);
    }

    @Test
    public void shouldMapFindCommodityEntityToFindCommodity() {
        // create mock objects
        CommodityEntity mockCommodityEntity = mock(CommodityEntity.class);
        Commodity mockCommodity = mock(Commodity.class);
        StationEntity mockStationEntity = mock(StationEntity.class);
        Station mockStation = mock(Station.class);
        SystemEntity mockSystemEntity = mock(SystemEntity.class);
        System mockSystem = mock(System.class);

        LocalDateTime pricesUpdatedAt = LocalDateTime.now();
        LocateCommodityEntity locateCommodityEntity = LocateCommodityEntity.builder()
                .pricesUpdatedAt(pricesUpdatedAt)
                .commodity(mockCommodityEntity)
                .station(mockStationEntity)
                .system(mockSystemEntity)
                .build();

        when(commodityMapper.map(mockCommodityEntity)).thenReturn(mockCommodity);
        when(stationMapper.map(mockStationEntity)).thenReturn(mockStation);
        when(systemMapper.map(mockSystemEntity)).thenReturn(mockSystem);

        LocateCommodity locateCommodity = underTest.map(locateCommodityEntity);

        assertThat(locateCommodity.getPricesUpdatedAt(), is(pricesUpdatedAt));
        assertThat(locateCommodity.getCommodity(), is(mockCommodity));
        assertThat(locateCommodity.getStation(), is(mockStation));
        assertThat(locateCommodity.getSystem(), is(mockSystem));

        verify(commodityMapper, times(1)).map(mockCommodityEntity);
        verify(stationMapper, times(1)).map(mockStationEntity);
        verify(systemMapper, times(1)).map(mockSystemEntity);
    }
}
