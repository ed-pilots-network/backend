package io.edpn.backend.trade.adapter.persistence.locatecommodity;

import io.edpn.backend.trade.adapter.persistence.LocateCommodityRepository;
import io.edpn.backend.trade.adapter.persistence.entity.MybatisLocateCommodityEntity;
import io.edpn.backend.trade.adapter.persistence.filter.MybatisLocateCommodityFilter;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisLocateCommodityRepository;
import io.edpn.backend.trade.application.domain.LocateCommodity;
import io.edpn.backend.trade.application.domain.filter.LocateCommodityFilter;
import io.edpn.backend.trade.application.dto.persistence.entity.mapper.LocateCommodityEntityMapper;
import io.edpn.backend.trade.application.dto.persistence.filter.mapper.PersistenceLocateCommodityFilterMapper;
import io.edpn.backend.trade.application.port.outgoing.locatecommodity.LocateCommodityByFilterPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LocateCommodityByFilterPortTest {
    
    @Mock
    private MybatisLocateCommodityRepository mybatisLocateCommodityRepository;
    
    @Mock
    private LocateCommodityEntityMapper<MybatisLocateCommodityEntity> mybatisLocateCommodityEntityMapper;
    
    @Mock
    private PersistenceLocateCommodityFilterMapper mybatisPersistenceLocateCommodityFilterMapper;
    
    private LocateCommodityByFilterPort underTest;
    
    @BeforeEach
    public void setUp() {
        underTest = new LocateCommodityRepository(mybatisLocateCommodityRepository, mybatisLocateCommodityEntityMapper, mybatisPersistenceLocateCommodityFilterMapper);
    }
    
    @Test
    void locateCommodityByFilter() {
        //mock objects
        MybatisLocateCommodityFilter locateCommodityFilterPersistence = mock(MybatisLocateCommodityFilter.class);
        MybatisLocateCommodityEntity locateCommodityEntity = mock(MybatisLocateCommodityEntity.class);
        LocateCommodityFilter locateCommodityFilter = mock(LocateCommodityFilter.class);
        LocateCommodity locateCommodity = mock(LocateCommodity.class);
        
        when(mybatisPersistenceLocateCommodityFilterMapper.map(locateCommodityFilter)).thenReturn(locateCommodityFilterPersistence);
        when(mybatisLocateCommodityRepository.locateCommodityByFilter(locateCommodityFilterPersistence)).thenReturn(Collections.singletonList(locateCommodityEntity));
        when(mybatisLocateCommodityEntityMapper.map(locateCommodityEntity)).thenReturn(locateCommodity);
        
        
        List<LocateCommodity> result = underTest.locateCommodityByFilter(locateCommodityFilter);
        
        verify(mybatisPersistenceLocateCommodityFilterMapper).map(locateCommodityFilter);
        verify(mybatisLocateCommodityRepository).locateCommodityByFilter(locateCommodityFilterPersistence);
        verify(mybatisLocateCommodityEntityMapper).map(locateCommodityEntity);
        verifyNoMoreInteractions(mybatisPersistenceLocateCommodityFilterMapper, mybatisLocateCommodityRepository, mybatisLocateCommodityEntityMapper);
        
        assertThat(result, equalTo(Collections.singletonList(locateCommodity)));
        
    }
}