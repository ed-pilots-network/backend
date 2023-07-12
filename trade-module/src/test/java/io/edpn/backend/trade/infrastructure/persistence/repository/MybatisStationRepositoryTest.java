package io.edpn.backend.trade.infrastructure.persistence.repository;

import io.edpn.backend.trade.domain.model.Station;
import io.edpn.backend.trade.domain.model.System;
import io.edpn.backend.trade.domain.repository.StationRepository;
import io.edpn.backend.trade.infrastructure.persistence.entity.StationEntity;
import io.edpn.backend.trade.infrastructure.persistence.mappers.entity.StationMapper;
import io.edpn.backend.trade.infrastructure.persistence.mappers.mybatis.MarketDatumEntityMapper;
import io.edpn.backend.trade.infrastructure.persistence.mappers.mybatis.StationEntityMapper;
import io.edpn.backend.util.IdGenerator;
import io.edpn.backend.util.exception.DatabaseEntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

//TODO: add coverage for saveMarketData
@ExtendWith(MockitoExtension.class)
class MybatisStationRepositoryTest {
    @Mock
    private IdGenerator idGenerator;
    @Mock
    private StationMapper stationMapper;
    @Mock
    private StationEntityMapper stationEntityMapper;
    @Mock
    private MarketDatumEntityMapper marketDatumEntityMapper;

    private StationRepository underTest;

    @BeforeEach
    void setUp() {
        underTest = new MybatisStationRepository(idGenerator, stationMapper, stationEntityMapper, marketDatumEntityMapper);
    }

    @Test
    void findById() {
        UUID id = UUID.randomUUID();
        StationEntity mockStationEntity = mock(StationEntity.class);
        Station mockStation = mock(Station.class);

        when(stationEntityMapper.findById(id)).thenReturn(Optional.of(mockStationEntity));
        when(stationMapper.map(mockStationEntity)).thenReturn(mockStation);

        Optional<Station> results = underTest.findById(id);

        verify(stationEntityMapper).findById(id);
        verify(stationMapper).map(mockStationEntity);
        verifyNoMoreInteractions(stationEntityMapper, stationMapper, idGenerator, marketDatumEntityMapper);

        assertThat(results.isPresent(), is(true));
        assertThat(results.get(), equalTo(mockStation));
    }

    @Test
    void findByIdNotFound() {
        UUID id = UUID.randomUUID();

        when(stationEntityMapper.findById(id)).thenReturn(Optional.empty());
        Optional<Station> result = underTest.findById(id);

        verify(stationEntityMapper).findById(id);
        verify(stationMapper, never()).map(any(StationEntity.class));
        verifyNoMoreInteractions(stationEntityMapper, stationMapper, idGenerator, marketDatumEntityMapper);

        assertThat(result, equalTo(Optional.empty()));
    }

    @Test
    void createWithoutId() {
        UUID id = UUID.randomUUID();
        Station inputStation = mock(Station.class);
        StationEntity mockStationEntityWithoutId = mock(StationEntity.class);
        StationEntity mockSavedStationEntity = mock(StationEntity.class);

        Station expected = mock(Station.class);

        when(stationMapper.map(inputStation)).thenReturn(mockStationEntityWithoutId);
        when(mockStationEntityWithoutId.getId()).thenReturn(null, id);
        when(idGenerator.generateId()).thenReturn(id);
        when(stationEntityMapper.findById(id)).thenReturn(Optional.ofNullable(mockSavedStationEntity));
        when(stationMapper.map(mockSavedStationEntity)).thenReturn(expected);

        Station result = underTest.create(inputStation);

        verify(stationMapper).map(inputStation);
        verify(idGenerator).generateId();
        verify(stationEntityMapper).insert(any());
        verify(stationEntityMapper).findById(id);
        verify(stationMapper).map(mockSavedStationEntity);
        verifyNoMoreInteractions(stationEntityMapper, stationMapper, idGenerator, marketDatumEntityMapper);

        assertThat(result, is(expected));
    }

    @Test
    void createWithId() {
        UUID id = UUID.randomUUID();
        Station inputStation = mock(Station.class);
        StationEntity mockStationEntity = mock(StationEntity.class);

        Station expected = mock(Station.class);

        when(stationMapper.map(inputStation)).thenReturn(mockStationEntity);
        when(mockStationEntity.getId()).thenReturn(id);
        when(stationEntityMapper.findById(id)).thenReturn(Optional.ofNullable(mockStationEntity));
        when(stationMapper.map(mockStationEntity)).thenReturn(expected);

        Station result = underTest.create(inputStation);

        verify(stationMapper).map(inputStation);
        verify(idGenerator, never()).generateId();
        verify(stationEntityMapper).insert(any());
        verify(stationEntityMapper).findById(id);
        verify(stationMapper).map(mockStationEntity);
        verifyNoMoreInteractions(stationEntityMapper, stationMapper, idGenerator, marketDatumEntityMapper);

        assertThat(result, is(expected));
    }

    @Test
    void createWithError() {
        UUID id = UUID.randomUUID();
        Station inputStation = mock(Station.class);
        StationEntity mockStationEntity = mock(StationEntity.class);

        when(stationMapper.map(inputStation)).thenReturn(mockStationEntity);
        when(mockStationEntity.getId()).thenReturn(id);
        when(stationEntityMapper.findById(id)).thenReturn(Optional.empty());

        Exception result = assertThrows(DatabaseEntityNotFoundException.class, () -> underTest.create(inputStation));

        verify(stationMapper).map(inputStation);
        verify(idGenerator, never()).generateId();
        verify(stationEntityMapper).insert(any());
        verify(stationEntityMapper).findById(id);
        verifyNoMoreInteractions(stationEntityMapper, stationMapper, idGenerator, marketDatumEntityMapper);

        assertThat(result.getMessage(), is("station with id: " + id + " could not be found after create"));
    }

    @Test
    void findOrCreateByNameNew() {
        String stationName = "Test Station";
        System system = mock(System.class);
        UUID id = UUID.randomUUID();
        UUID systemId = UUID.randomUUID();
        StationEntity mockStationEntityWithoutId = mock(StationEntity.class);
        StationEntity mockSavedStationEntity = mock(StationEntity.class);

        Station expected = mock(Station.class);

        when(system.getId()).thenReturn(systemId);
        when(stationEntityMapper.findBySystemIdAndStationName(systemId, stationName)).thenReturn(Optional.empty());
        when(stationMapper.map(argThat((Station input) -> input.getId() == null && input.getName().equals(stationName)))).thenReturn(mockStationEntityWithoutId);
        when(mockStationEntityWithoutId.getId()).thenReturn(null, id);
        when(idGenerator.generateId()).thenReturn(id);
        when(stationEntityMapper.findById(id)).thenReturn(Optional.ofNullable(mockSavedStationEntity));
        when(stationMapper.map(mockSavedStationEntity)).thenReturn(expected);

        Station result = underTest.findOrCreateBySystemAndStationName(system, stationName);

        verify(stationEntityMapper).findBySystemIdAndStationName(systemId, stationName);
        verify(stationMapper).map(argThat((Station input) -> input.getId() == null && input.getName().equals(stationName)));
        verify(idGenerator).generateId();
        verify(stationEntityMapper).insert(any());
        verify(stationEntityMapper).findById(id);
        verify(stationMapper).map(mockSavedStationEntity);
        verifyNoMoreInteractions(stationEntityMapper, stationMapper, idGenerator, marketDatumEntityMapper);

        assertThat(result, is(expected));
    }

    @Test
    void findOrCreateByNameFound() {
        String name = "Test Station";
        System system = mock(System.class);
        UUID systemId = UUID.randomUUID();
        StationEntity mockSavedStationEntity = mock(StationEntity.class);

        Station expected = mock(Station.class);

        when(system.getId()).thenReturn(systemId);
        when(stationEntityMapper.findBySystemIdAndStationName(systemId, name)).thenReturn(Optional.of(mockSavedStationEntity));
        when(stationMapper.map(mockSavedStationEntity)).thenReturn(expected);

        Station result = underTest.findOrCreateBySystemAndStationName(system, name);

        verify(stationEntityMapper).findBySystemIdAndStationName(systemId, name);
        verify(stationMapper).map(mockSavedStationEntity);
        verifyNoMoreInteractions(stationEntityMapper, stationMapper, idGenerator, marketDatumEntityMapper);

        assertThat(result, is(expected));
    }
}