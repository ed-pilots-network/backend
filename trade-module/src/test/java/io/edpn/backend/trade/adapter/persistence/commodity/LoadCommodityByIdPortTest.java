package io.edpn.backend.trade.adapter.persistence.commodity;

import io.edpn.backend.trade.adapter.persistence.CommodityRepository;
import io.edpn.backend.trade.adapter.persistence.entity.MybatisCommodityEntity;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisCommodityRepository;
import io.edpn.backend.trade.application.domain.Commodity;
import io.edpn.backend.trade.application.dto.persistence.entity.mapper.CommodityEntityMapper;
import io.edpn.backend.trade.application.port.outgoing.commodity.LoadCommodityByIdPort;
import io.edpn.backend.util.IdGenerator;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoadCommodityByIdPortTest {
    
    @Mock
    private IdGenerator idGenerator;
    
    @Mock
    private CommodityEntityMapper<MybatisCommodityEntity> mybatisCommodityEntityMapper;
    
    @Mock
    private MybatisCommodityRepository mybatisCommodityRepository;
    
    private LoadCommodityByIdPort underTest;
    
    @BeforeEach
    public void setUp(){
        underTest = new CommodityRepository(idGenerator, mybatisCommodityEntityMapper, mybatisCommodityRepository);
    }
    
    @Test
    void loadById() {
        UUID id = UUID.randomUUID();
        MybatisCommodityEntity mockCommodityEntity = mock(MybatisCommodityEntity.class);
        Commodity mockCommodity = mock(Commodity.class);
        
        when(mybatisCommodityRepository.findById(id)).thenReturn(Optional.of(mockCommodityEntity));
        when(mybatisCommodityEntityMapper.map(mockCommodityEntity)).thenReturn(mockCommodity);
        
        Optional<Commodity> results = underTest.loadById(id);
        
        verify(mybatisCommodityRepository).findById(id);
        verify(mybatisCommodityEntityMapper).map(mockCommodityEntity);
        verifyNoMoreInteractions(mybatisCommodityRepository, mybatisCommodityEntityMapper, idGenerator);
        
        assertThat(results.isPresent(), is(true));
        assertThat(results.get(), equalTo(mockCommodity));
    }
    
    @Test
    void loadByIdNotFound() {
        UUID id = UUID.randomUUID();
        
        when(mybatisCommodityRepository.findById(id)).thenReturn(Optional.empty());
        Optional<Commodity> result = underTest.loadById(id);
        
        verify(mybatisCommodityRepository).findById(id);
        verify(mybatisCommodityEntityMapper, never()).map(any(MybatisCommodityEntity.class));
        verifyNoMoreInteractions(mybatisCommodityRepository, mybatisCommodityEntityMapper, idGenerator);
        
        assertThat(result, equalTo(Optional.empty()));
    }
}
