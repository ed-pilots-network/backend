package io.edpn.backend.trade.adapter.persistence.commodity;

import io.edpn.backend.trade.adapter.persistence.CommodityRepository;
import io.edpn.backend.trade.adapter.persistence.entity.MybatisCommodityEntity;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisCommodityRepository;
import io.edpn.backend.trade.application.domain.Commodity;
import io.edpn.backend.trade.application.dto.persistence.entity.mapper.CommodityEntityMapper;
import io.edpn.backend.trade.application.port.outgoing.commodity.CreateCommodityPort;
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
public class CreateCommodityPortTest {
    
    @Mock
    private IdGenerator idGenerator;
    
    @Mock
    private CommodityEntityMapper<MybatisCommodityEntity> mybatisCommodityEntityMapper;
    
    @Mock
    private MybatisCommodityRepository mybatisCommodityRepository;
    
    private CreateCommodityPort underTest;
    
    @BeforeEach
    public void setUp(){
        underTest = new CommodityRepository(idGenerator, mybatisCommodityEntityMapper, mybatisCommodityRepository);
    }
    
    @Test
    void createWithoutId() {
        UUID id = UUID.randomUUID();
        Commodity inputCommodity = mock(Commodity.class);
        MybatisCommodityEntity mockCommodityEntityWithoutId = mock(MybatisCommodityEntity.class);
        MybatisCommodityEntity mockSavedCommodityEntity = mock(MybatisCommodityEntity.class);
        
        Commodity expected = mock(Commodity.class);
        
        when(mybatisCommodityEntityMapper.map(inputCommodity)).thenReturn(mockCommodityEntityWithoutId);
        when(mockCommodityEntityWithoutId.getId()).thenReturn(null, id);
        when(idGenerator.generateId()).thenReturn(id);
        when(mybatisCommodityRepository.findById(id)).thenReturn(Optional.of(mockSavedCommodityEntity));
        when(mybatisCommodityEntityMapper.map(mockSavedCommodityEntity)).thenReturn(expected);
        
        Commodity result = underTest.create(inputCommodity);
        
        verify(mybatisCommodityEntityMapper).map(inputCommodity);
        verify(idGenerator).generateId();
        verify(mybatisCommodityRepository).insert(any());
        verify(mybatisCommodityRepository).findById(id);
        verify(mybatisCommodityEntityMapper).map(mockSavedCommodityEntity);
        verifyNoMoreInteractions(mybatisCommodityEntityMapper, mybatisCommodityRepository, idGenerator);
        
        assertThat(result, is(expected));
    }
    
    @Test
    void createWithId() {
        UUID id = UUID.randomUUID();
        Commodity inputCommodity = mock(Commodity.class);
        MybatisCommodityEntity mockCommodityEntity = mock(MybatisCommodityEntity.class);
        
        Commodity expected = mock(Commodity.class);
        
        when(mybatisCommodityEntityMapper.map(inputCommodity)).thenReturn(mockCommodityEntity);
        when(mockCommodityEntity.getId()).thenReturn(id);
        when(mybatisCommodityRepository.findById(id)).thenReturn(Optional.of(mockCommodityEntity));
        when(mybatisCommodityEntityMapper.map(mockCommodityEntity)).thenReturn(expected);
        
        Commodity result = underTest.create(inputCommodity);
        
        verify(mybatisCommodityEntityMapper).map(inputCommodity);
        verify(idGenerator, never()).generateId();
        verify(mybatisCommodityRepository).insert(any());
        verify(mybatisCommodityRepository).findById(id);
        verify(mybatisCommodityEntityMapper).map(mockCommodityEntity);
        verifyNoMoreInteractions(mybatisCommodityRepository, mybatisCommodityEntityMapper, idGenerator);
        
        assertThat(result, is(expected));
    }
    
    @Test
    void createWithError() {
        UUID id = UUID.randomUUID();
        Commodity inputCommodity = mock(Commodity.class);
        MybatisCommodityEntity mockCommodityEntity = mock(MybatisCommodityEntity.class);
        
        when(inputCommodity.getId()).thenReturn(id);
        when(mybatisCommodityEntityMapper.map(inputCommodity)).thenReturn(mockCommodityEntity);
        when(mockCommodityEntity.getId()).thenReturn(id);
        when(mybatisCommodityRepository.findById(id)).thenReturn(Optional.empty());
        
        Exception result = assertThrows(DatabaseEntityNotFoundException.class, () -> underTest.create(inputCommodity));
        
        verify(mybatisCommodityEntityMapper).map(inputCommodity);
        verify(idGenerator, never()).generateId();
        verify(mybatisCommodityRepository).insert(any());
        verify(mybatisCommodityRepository).findById(id);
        verifyNoMoreInteractions(mybatisCommodityRepository, mybatisCommodityEntityMapper, idGenerator);
        
        assertThat(result.getMessage(), is("commodity with id: " + id + " could not be found after create"));
    }
}
