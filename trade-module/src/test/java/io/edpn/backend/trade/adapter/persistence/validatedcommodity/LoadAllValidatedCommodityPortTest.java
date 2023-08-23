package io.edpn.backend.trade.adapter.persistence.validatedcommodity;

import io.edpn.backend.trade.adapter.persistence.ValidatedCommodityRepository;
import io.edpn.backend.trade.adapter.persistence.entity.MybatisValidatedCommodityEntity;
import io.edpn.backend.trade.adapter.persistence.filter.mapper.MybatisPersistenceFindCommodityFilterMapper;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisValidatedCommodityRepository;
import io.edpn.backend.trade.application.domain.ValidatedCommodity;
import io.edpn.backend.trade.application.dto.persistence.entity.mapper.ValidatedCommodityEntityMapper;
import io.edpn.backend.trade.application.port.outgoing.validatedcommodity.LoadAllValidatedCommodityPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoadAllValidatedCommodityPortTest {
    
    @Mock
    private MybatisValidatedCommodityRepository mybatisValidatedCommodityRepository;
    
    @Mock
    private ValidatedCommodityEntityMapper<MybatisValidatedCommodityEntity> mybatisValidatedCommodityEntityMapper;
    
    @Mock
    private MybatisPersistenceFindCommodityFilterMapper mybatisPersistenceFindCommodityFilterMapper;
    
    private LoadAllValidatedCommodityPort underTest;
    
    @BeforeEach
    public void setUp(){
        underTest = new ValidatedCommodityRepository(mybatisValidatedCommodityRepository, mybatisValidatedCommodityEntityMapper, mybatisPersistenceFindCommodityFilterMapper);
    }
    
    @Test
    void findAll() {
        MybatisValidatedCommodityEntity validatedCommodityEntity = mock(MybatisValidatedCommodityEntity.class);
        ValidatedCommodity validatedCommodity = mock(ValidatedCommodity.class);
        
        
        when(mybatisValidatedCommodityRepository.findAll()).thenReturn(List.of(validatedCommodityEntity));
        when(mybatisValidatedCommodityEntityMapper.map(validatedCommodityEntity)).thenReturn(validatedCommodity);
        
        List<ValidatedCommodity> result = underTest.loadAll();
        
        verify(mybatisValidatedCommodityRepository).findAll();
        verify(mybatisValidatedCommodityEntityMapper).map(validatedCommodityEntity);
        verifyNoMoreInteractions(mybatisPersistenceFindCommodityFilterMapper, mybatisValidatedCommodityRepository, mybatisValidatedCommodityEntityMapper);
        
        assertThat(result, equalTo(List.of(validatedCommodity)));
    }
}
