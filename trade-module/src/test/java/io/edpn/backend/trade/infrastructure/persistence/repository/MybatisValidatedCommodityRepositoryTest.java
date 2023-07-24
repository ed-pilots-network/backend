package io.edpn.backend.trade.infrastructure.persistence.repository;

import io.edpn.backend.trade.domain.filter.v1.FindCommodityFilter;
import io.edpn.backend.trade.domain.model.ValidatedCommodity;
import io.edpn.backend.trade.domain.repository.ValidatedCommodityRepository;
import io.edpn.backend.trade.infrastructure.persistence.entity.ValidatedCommodityEntity;
import io.edpn.backend.trade.infrastructure.persistence.filter.FindCommodityFilterPersistence;
import io.edpn.backend.trade.infrastructure.persistence.mappers.entity.ValidatedCommodityMapper;
import io.edpn.backend.trade.infrastructure.persistence.mappers.filter.FindCommodityFilterMapper;
import io.edpn.backend.trade.infrastructure.persistence.mappers.mybatis.ValidatedCommodityEntityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MybatisValidatedCommodityRepositoryTest {
    
    @Mock
    private ValidatedCommodityMapper validatedCommodityMapper;
    @Mock
    private ValidatedCommodityEntityMapper validatedCommodityEntityMapper;
    @Mock
    private FindCommodityFilterMapper findCommodityFilterMapper;
    
    private ValidatedCommodityRepository underTest;
    
    @BeforeEach
    void setUp() {
        underTest = new MybatisValidatedCommodityRepository(validatedCommodityMapper, validatedCommodityEntityMapper, findCommodityFilterMapper);
    }
    
    @Test
    void findCommodityByName() {
        ValidatedCommodityEntity validatedCommodityEntity = mock(ValidatedCommodityEntity.class);
        ValidatedCommodity validatedCommodity = mock(ValidatedCommodity.class);
        String displayName = "DisplayName";
        
        
        when(validatedCommodityEntityMapper.findByName(displayName)).thenReturn(Optional.of(validatedCommodityEntity));
        when(validatedCommodityMapper.map(validatedCommodityEntity)).thenReturn(validatedCommodity);
        
        Optional<ValidatedCommodity> result = underTest.findByName(displayName);
        
        verify(validatedCommodityEntityMapper).findByName(displayName);
        verify(validatedCommodityMapper).map(validatedCommodityEntity);
        verifyNoMoreInteractions(findCommodityFilterMapper, validatedCommodityEntityMapper, validatedCommodityMapper);
        
        assertThat(result, equalTo(Optional.of(validatedCommodity)));
    }
    
    @Test
    void findAll() {
        ValidatedCommodityEntity validatedCommodityEntity = mock(ValidatedCommodityEntity.class);
        ValidatedCommodity validatedCommodity = mock(ValidatedCommodity.class);
        
        
        when(validatedCommodityEntityMapper.findAll()).thenReturn(List.of(validatedCommodityEntity));
        when(validatedCommodityMapper.map(validatedCommodityEntity)).thenReturn(validatedCommodity);
        
        List<ValidatedCommodity> result = underTest.findAll();
        
        verify(validatedCommodityEntityMapper).findAll();
        verify(validatedCommodityMapper).map(validatedCommodityEntity);
        verifyNoMoreInteractions(findCommodityFilterMapper, validatedCommodityEntityMapper, validatedCommodityMapper);
        
        assertThat(result, equalTo(List.of(validatedCommodity)));
    }
    
    @Test
    void findCommodityByFilter() {
        FindCommodityFilterPersistence findCommodityFilterPersistence = mock(FindCommodityFilterPersistence.class);
        ValidatedCommodityEntity validatedCommodityEntity = mock(ValidatedCommodityEntity.class);
        FindCommodityFilter findCommodityFilter = mock(FindCommodityFilter.class);
        ValidatedCommodity validatedCommodity = mock(ValidatedCommodity.class);
        
        
        when(findCommodityFilterMapper.map(findCommodityFilter)).thenReturn(findCommodityFilterPersistence);
        when(validatedCommodityEntityMapper.findByFilter(findCommodityFilterPersistence)).thenReturn(List.of(validatedCommodityEntity));
        when(validatedCommodityMapper.map(validatedCommodityEntity)).thenReturn(validatedCommodity);
        
        List<ValidatedCommodity> result = underTest.findByFilter(findCommodityFilter);
        
        verify(findCommodityFilterMapper).map(findCommodityFilter);
        verify(validatedCommodityEntityMapper).findByFilter(findCommodityFilterPersistence);
        verify(validatedCommodityMapper).map(validatedCommodityEntity);
        verifyNoMoreInteractions(findCommodityFilterMapper, validatedCommodityEntityMapper, validatedCommodityMapper);
        
        assertThat(result, equalTo(List.of(validatedCommodity)));
    }
}