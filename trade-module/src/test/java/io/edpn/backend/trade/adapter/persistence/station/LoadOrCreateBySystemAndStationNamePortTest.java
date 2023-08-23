package io.edpn.backend.trade.adapter.persistence.station;

import io.edpn.backend.trade.adapter.persistence.StationRepository;
import io.edpn.backend.trade.adapter.persistence.entity.MybatisStationEntity;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisMarketDatumRepository;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisStationRepository;
import io.edpn.backend.trade.application.domain.Station;
import io.edpn.backend.trade.application.domain.System;
import io.edpn.backend.trade.application.dto.persistence.entity.mapper.StationEntityMapper;
import io.edpn.backend.trade.application.port.outgoing.station.LoadOrCreateBySystemAndStationNamePort;
import io.edpn.backend.util.IdGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoadOrCreateBySystemAndStationNamePortTest {
    
    @Mock
    private IdGenerator idGenerator;
    
    @Mock
    private StationEntityMapper<MybatisStationEntity> mybatisStationEntityMapper;
    
    @Mock
    private MybatisStationRepository mybatisStationRepository;
    
    @Mock
    private MybatisMarketDatumRepository mybatisMarketDatumRepository;
    
    private LoadOrCreateBySystemAndStationNamePort underTest;
    
    @BeforeEach
    public void setUp(){
        underTest = new StationRepository(idGenerator, mybatisStationEntityMapper, mybatisStationRepository, mybatisMarketDatumRepository);
    }
    
    @Test
    void findOrCreateByNameNew() {
        String stationName = "Test Station";
        System system = mock(System.class);
        UUID id = UUID.randomUUID();
        UUID systemId = UUID.randomUUID();
        MybatisStationEntity mockStationEntityWithoutId = mock(MybatisStationEntity.class);
        MybatisStationEntity mockSavedStationEntity = mock(MybatisStationEntity.class);
        
        Station expected = mock(Station.class);
        
        when(system.getId()).thenReturn(systemId);
        when(mybatisStationRepository.findBySystemIdAndStationName(systemId, stationName)).thenReturn(Optional.empty());
        when(mybatisStationEntityMapper.map(argThat((Station input) -> input.getId() == null && input.getName().equals(stationName)))).thenReturn(mockStationEntityWithoutId);
        when(mockStationEntityWithoutId.getId()).thenReturn(null, id);
        when(idGenerator.generateId()).thenReturn(id);
        when(mybatisStationRepository.findById(id)).thenReturn(Optional.ofNullable(mockSavedStationEntity));
        when(mybatisStationEntityMapper.map(mockSavedStationEntity)).thenReturn(expected);
        
        Station result = underTest.loadOrCreateBySystemAndStationName(system, stationName);
        
        verify(mybatisStationRepository).findBySystemIdAndStationName(systemId, stationName);
        verify(mybatisStationEntityMapper).map(argThat((Station input) -> input.getId() == null && input.getName().equals(stationName)));
        verify(idGenerator).generateId();
        verify(mybatisStationRepository).insert(any());
        verify(mybatisStationRepository).findById(id);
        verify(mybatisStationEntityMapper).map(mockSavedStationEntity);
        verifyNoMoreInteractions(mybatisStationRepository, mybatisStationEntityMapper, idGenerator, mybatisMarketDatumRepository);
        
        assertThat(result, is(expected));
    }
    
    @Test
    void findOrCreateByNameFound() {
        String name = "Test Station";
        System system = mock(System.class);
        UUID systemId = UUID.randomUUID();
        MybatisStationEntity mockSavedStationEntity = mock(MybatisStationEntity.class);
        
        Station expected = mock(Station.class);
        
        when(system.getId()).thenReturn(systemId);
        when(mybatisStationRepository.findBySystemIdAndStationName(systemId, name)).thenReturn(Optional.of(mockSavedStationEntity));
        when(mybatisStationEntityMapper.map(mockSavedStationEntity)).thenReturn(expected);
        
        Station result = underTest.loadOrCreateBySystemAndStationName(system, name);
        
        verify(mybatisStationRepository).findBySystemIdAndStationName(systemId, name);
        verify(mybatisStationEntityMapper).map(mockSavedStationEntity);
        verifyNoMoreInteractions(mybatisStationRepository, mybatisStationEntityMapper, idGenerator, mybatisMarketDatumRepository);
        
        assertThat(result, is(expected));
    }
}
