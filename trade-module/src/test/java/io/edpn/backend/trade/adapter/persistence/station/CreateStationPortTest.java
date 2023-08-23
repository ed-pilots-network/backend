package io.edpn.backend.trade.adapter.persistence.station;

import io.edpn.backend.trade.adapter.persistence.StationRepository;
import io.edpn.backend.trade.adapter.persistence.entity.MybatisStationEntity;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisMarketDatumRepository;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisStationRepository;
import io.edpn.backend.trade.application.domain.Station;
import io.edpn.backend.trade.application.dto.persistence.entity.mapper.StationEntityMapper;
import io.edpn.backend.trade.application.port.outgoing.station.CreateStationPort;
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
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateStationPortTest {
    
    @Mock
    private IdGenerator idGenerator;
    
    @Mock
    private StationEntityMapper<MybatisStationEntity> mybatisStationEntityMapper;
    
    @Mock
    private MybatisStationRepository mybatisStationRepository;
    
    @Mock
    private MybatisMarketDatumRepository mybatisMarketDatumRepository;
    
    private CreateStationPort underTest;
    
    @BeforeEach
    public void setUp(){
        underTest = new StationRepository(idGenerator, mybatisStationEntityMapper, mybatisStationRepository, mybatisMarketDatumRepository);
    }
    
    @Test
    void createWithoutId() {
        UUID id = UUID.randomUUID();
        Station inputStation = mock(Station.class);
        MybatisStationEntity mockStationEntityWithoutId = mock(MybatisStationEntity.class);
        MybatisStationEntity mockSavedStationEntity = mock(MybatisStationEntity.class);
        
        Station expected = mock(Station.class);
        
        when(mybatisStationEntityMapper.map(inputStation)).thenReturn(mockStationEntityWithoutId);
        when(mockStationEntityWithoutId.getId()).thenReturn(null, id);
        when(idGenerator.generateId()).thenReturn(id);
        when(mybatisStationRepository.findById(id)).thenReturn(Optional.ofNullable(mockSavedStationEntity));
        when(mybatisStationEntityMapper.map(mockSavedStationEntity)).thenReturn(expected);
        
        Station result = underTest.create(inputStation);
        
        verify(mybatisStationEntityMapper).map(inputStation);
        verify(idGenerator).generateId();
        verify(mybatisStationRepository).insert(any());
        verify(mybatisStationRepository).findById(id);
        verify(mybatisStationEntityMapper).map(mockSavedStationEntity);
        verifyNoMoreInteractions(mybatisStationRepository, mybatisStationEntityMapper, idGenerator, mybatisMarketDatumRepository);
        
        assertThat(result, is(expected));
    }
    
    @Test
    void createWithId() {
        UUID id = UUID.randomUUID();
        Station inputStation = mock(Station.class);
        MybatisStationEntity mockStationEntity = mock(MybatisStationEntity.class);
        
        Station expected = mock(Station.class);
        
        when(mybatisStationEntityMapper.map(inputStation)).thenReturn(mockStationEntity);
        when(mockStationEntity.getId()).thenReturn(id);
        when(mybatisStationRepository.findById(id)).thenReturn(Optional.of(mockStationEntity));
        when(mybatisStationEntityMapper.map(mockStationEntity)).thenReturn(expected);
        
        Station result = underTest.create(inputStation);
        
        verify(mybatisStationEntityMapper).map(inputStation);
        verify(idGenerator, never()).generateId();
        verify(mybatisStationRepository).insert(any());
        verify(mybatisStationRepository).findById(id);
        verify(mybatisStationEntityMapper).map(mockStationEntity);
        verifyNoMoreInteractions(mybatisStationRepository, mybatisStationEntityMapper, idGenerator, mybatisMarketDatumRepository);
        
        assertThat(result, is(expected));
    }
    
    @Test
    void createWithError() {
        UUID id = UUID.randomUUID();
        Station inputStation = mock(Station.class);
        MybatisStationEntity mockStationEntity = mock(MybatisStationEntity.class);
        
        when(mybatisStationEntityMapper.map(inputStation)).thenReturn(mockStationEntity);
        when(mockStationEntity.getId()).thenReturn(id);
        when(mybatisStationRepository.findById(id)).thenReturn(Optional.empty());
        
        Exception result = assertThrows(DatabaseEntityNotFoundException.class, () -> underTest.create(inputStation));
        
        verify(mybatisStationEntityMapper).map(inputStation);
        verify(idGenerator, never()).generateId();
        verify(mybatisStationRepository).insert(any());
        verify(mybatisStationRepository).findById(id);
        verifyNoMoreInteractions(mybatisStationRepository, mybatisStationEntityMapper, idGenerator, mybatisMarketDatumRepository);
        
        assertThat(result.getMessage(), is("station with id: " + id + " could not be found after create"));
    }
}
