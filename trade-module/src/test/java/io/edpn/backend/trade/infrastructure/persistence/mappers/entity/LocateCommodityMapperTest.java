package io.edpn.backend.trade.infrastructure.persistence.mappers.entity;

import io.edpn.backend.trade.domain.model.LocateCommodity;
import io.edpn.backend.trade.domain.model.Station;
import io.edpn.backend.trade.domain.model.System;
import io.edpn.backend.trade.domain.model.ValidatedCommodity;
import io.edpn.backend.trade.infrastructure.persistence.entity.LocateCommodityEntity;
import io.edpn.backend.trade.infrastructure.persistence.entity.StationEntity;
import io.edpn.backend.trade.infrastructure.persistence.entity.SystemEntity;
import io.edpn.backend.trade.infrastructure.persistence.entity.ValidatedCommodityEntity;
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
    private ValidatedCommodityMapper validatedCommodityMapper;

    @Mock
    private SystemMapper systemMapper;

    @Mock
    private StationMapper stationMapper;

    @InjectMocks
    private LocateCommodityMapper underTest;

    @BeforeEach
    public void setUp() {
        underTest = new LocateCommodityMapper(validatedCommodityMapper, systemMapper, stationMapper);
    }

    @Test
    public void shouldMapLocateCommodityEntityToLocateCommodity() {
        // create mock objects
        ValidatedCommodityEntity mockCommodityEntity = mock(ValidatedCommodityEntity.class);
        ValidatedCommodity mockCommodity = mock(ValidatedCommodity.class);
        StationEntity mockStationEntity = mock(StationEntity.class);
        Station mockStation = mock(Station.class);
        SystemEntity mockSystemEntity = mock(SystemEntity.class);
        System mockSystem = mock(System.class);

        LocalDateTime pricesUpdatedAt = LocalDateTime.now();
        LocateCommodityEntity locateCommodityEntity = LocateCommodityEntity.builder()
                .pricesUpdatedAt(pricesUpdatedAt)
                .validatedCommodity(mockCommodityEntity)
                .station(mockStationEntity)
                .system(mockSystemEntity)
                .build();

        when(validatedCommodityMapper.map(mockCommodityEntity)).thenReturn(mockCommodity);
        when(stationMapper.map(mockStationEntity)).thenReturn(mockStation);
        when(systemMapper.map(mockSystemEntity)).thenReturn(mockSystem);

        LocateCommodity locateCommodity = underTest.map(locateCommodityEntity);

        assertThat(locateCommodity.getPricesUpdatedAt(), is(pricesUpdatedAt));
        assertThat(locateCommodity.getValidatedCommodity(), is(mockCommodity));
        assertThat(locateCommodity.getStation(), is(mockStation));
        assertThat(locateCommodity.getSystem(), is(mockSystem));

        verify(validatedCommodityMapper, times(1)).map(mockCommodityEntity);
        verify(stationMapper, times(1)).map(mockStationEntity);
        verify(systemMapper, times(1)).map(mockSystemEntity);
    }
}
