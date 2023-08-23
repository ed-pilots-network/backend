package io.edpn.backend.trade.adapter.persistence.commodity;

import io.edpn.backend.trade.adapter.persistence.CommodityRepository;
import io.edpn.backend.trade.adapter.persistence.entity.MybatisCommodityEntity;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisCommodityRepository;
import io.edpn.backend.trade.application.domain.Commodity;
import io.edpn.backend.trade.application.dto.persistence.entity.mapper.CommodityEntityMapper;
import io.edpn.backend.trade.application.port.outgoing.commodity.LoadOrCreateCommodityByNamePort;
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
public class LoadOrCreateCommodityByNamePortTest {
    
    @Mock
    private IdGenerator idGenerator;
    
    @Mock
    private CommodityEntityMapper<MybatisCommodityEntity> mybatisCommodityEntityMapper;
    
    @Mock
    private MybatisCommodityRepository mybatisCommodityRepository;
    
    private LoadOrCreateCommodityByNamePort underTest;
    
    @BeforeEach
    public void setUp(){
        underTest = new CommodityRepository(idGenerator, mybatisCommodityEntityMapper, mybatisCommodityRepository);
    }
    
    @Test
    void findOrCreateByNameNew() {
        String name = "Test Commodity";
        UUID id = UUID.randomUUID();
        MybatisCommodityEntity mockCommodityEntityWithoutId = mock(MybatisCommodityEntity.class);
        MybatisCommodityEntity mockSavedCommodityEntity = mock(MybatisCommodityEntity.class);
        
        Commodity expected = mock(Commodity.class);
        
        when(mybatisCommodityRepository.findByName(name)).thenReturn(Optional.empty());
        when(mybatisCommodityEntityMapper.map(argThat((Commodity input) -> input.getId() == null && input.getName().equals(name)))).thenReturn(mockCommodityEntityWithoutId);
        when(mockCommodityEntityWithoutId.getId()).thenReturn(null, id);
        when(idGenerator.generateId()).thenReturn(id);
        when(mybatisCommodityRepository.findById(id)).thenReturn(Optional.ofNullable(mockSavedCommodityEntity));
        when(mybatisCommodityEntityMapper.map(mockSavedCommodityEntity)).thenReturn(expected);
        
        Commodity result = underTest.loadOrCreate(name);
        
        verify(mybatisCommodityRepository).findByName(name);
        verify(mybatisCommodityEntityMapper).map(argThat((Commodity input) -> input.getId() == null && input.getName().equals(name)));
        verify(idGenerator).generateId();
        verify(mybatisCommodityRepository).insert(any());
        verify(mybatisCommodityRepository).findById(id);
        verify(mybatisCommodityEntityMapper).map(mockSavedCommodityEntity);
        verifyNoMoreInteractions(mybatisCommodityRepository, mybatisCommodityEntityMapper, idGenerator);
        
        assertThat(result, is(expected));
    }
    
    @Test
    void findOrCreateByNameFound() {
        String name = "Test Commodity";
        MybatisCommodityEntity mockSavedCommodityEntity = mock(MybatisCommodityEntity.class);
        
        Commodity expected = mock(Commodity.class);
        
        when(mybatisCommodityRepository.findByName(name)).thenReturn(Optional.of(mockSavedCommodityEntity));
        when(mybatisCommodityEntityMapper.map(mockSavedCommodityEntity)).thenReturn(expected);
        
        Commodity result = underTest.loadOrCreate(name);
        
        verify(mybatisCommodityRepository).findByName(name);
        verify(mybatisCommodityEntityMapper).map(mockSavedCommodityEntity);
        verifyNoMoreInteractions(mybatisCommodityRepository, mybatisCommodityEntityMapper, idGenerator);
        
        assertThat(result, is(expected));
    }
}
