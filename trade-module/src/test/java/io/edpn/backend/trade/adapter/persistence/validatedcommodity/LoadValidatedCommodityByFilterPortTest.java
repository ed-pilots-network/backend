package io.edpn.backend.trade.adapter.persistence.validatedcommodity;

import io.edpn.backend.trade.adapter.persistence.ValidatedCommodityRepository;
import io.edpn.backend.trade.adapter.persistence.entity.MybatisValidatedCommodityEntity;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.MybatisValidatedCommodityEntityMapper;
import io.edpn.backend.trade.adapter.persistence.filter.MybatisFindCommodityFilter;
import io.edpn.backend.trade.adapter.persistence.filter.mapper.MybatisFindCommodityFilterMapper;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisValidatedCommodityRepository;
import io.edpn.backend.trade.application.domain.ValidatedCommodity;
import io.edpn.backend.trade.application.port.outgoing.validatedcommodity.LoadValidatedCommodityByFilterPort;
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
public class LoadValidatedCommodityByFilterPortTest {

    @Mock
    private MybatisValidatedCommodityRepository mybatisValidatedCommodityRepository;

    @Mock
    private MybatisValidatedCommodityEntityMapper mybatisValidatedCommodityEntityMapper;

    @Mock
    private MybatisFindCommodityFilterMapper mybatisFindCommodityFilterMapper;

    private LoadValidatedCommodityByFilterPort underTest;

    @BeforeEach
    public void setUp() {
        underTest = new ValidatedCommodityRepository(mybatisValidatedCommodityRepository, mybatisValidatedCommodityEntityMapper, mybatisFindCommodityFilterMapper);
    }

    @Test
    void findCommodityByFilter() {
        MybatisFindCommodityFilter mybatisFindCommodityFilterPersistence = mock(MybatisFindCommodityFilter.class);
        MybatisValidatedCommodityEntity mybatisValidatedCommodityEntity = mock(MybatisValidatedCommodityEntity.class);
        io.edpn.backend.trade.application.domain.filter.FindCommodityFilter findCommodityFilter = mock(io.edpn.backend.trade.application.domain.filter.FindCommodityFilter.class);
        ValidatedCommodity validatedCommodity = mock(ValidatedCommodity.class);


        when(mybatisFindCommodityFilterMapper.map(findCommodityFilter)).thenReturn(mybatisFindCommodityFilterPersistence);
        when(mybatisValidatedCommodityRepository.findByFilter(mybatisFindCommodityFilterPersistence)).thenReturn(List.of(mybatisValidatedCommodityEntity));
        when(mybatisValidatedCommodityEntityMapper.map(mybatisValidatedCommodityEntity)).thenReturn(validatedCommodity);

        List<ValidatedCommodity> result = underTest.loadByFilter(findCommodityFilter);

        verify(mybatisFindCommodityFilterMapper).map(findCommodityFilter);
        verify(mybatisValidatedCommodityRepository).findByFilter(mybatisFindCommodityFilterPersistence);
        verify(mybatisValidatedCommodityEntityMapper).map(mybatisValidatedCommodityEntity);
        verifyNoMoreInteractions(mybatisFindCommodityFilterMapper, mybatisValidatedCommodityRepository, mybatisValidatedCommodityEntityMapper);

        assertThat(result, equalTo(List.of(validatedCommodity)));
    }
}
