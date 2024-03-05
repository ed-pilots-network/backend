package io.edpn.backend.trade.adapter.persistence.validatedcommodity;

import io.edpn.backend.trade.adapter.persistence.ValidatedCommodityRepository;
import io.edpn.backend.trade.adapter.persistence.entity.ValidatedCommodityEntity;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.ValidatedCommodityEntityMapper;
import io.edpn.backend.trade.adapter.persistence.filter.FindCommodityFilter;
import io.edpn.backend.trade.adapter.persistence.filter.mapper.FindCommodityFilterMapper;
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
    private ValidatedCommodityEntityMapper validatedCommodityEntityMapper;

    @Mock
    private FindCommodityFilterMapper findCommodityFilterMapper;

    private LoadValidatedCommodityByFilterPort underTest;

    @BeforeEach
    public void setUp() {
        underTest = new ValidatedCommodityRepository(mybatisValidatedCommodityRepository, validatedCommodityEntityMapper, findCommodityFilterMapper);
    }

    @Test
    void findCommodityByFilter() {
        FindCommodityFilter findCommodityFilterPersistence = mock(FindCommodityFilter.class);
        ValidatedCommodityEntity validatedCommodityEntity = mock(ValidatedCommodityEntity.class);
        io.edpn.backend.trade.application.domain.filter.FindCommodityFilter findCommodityFilter = mock(io.edpn.backend.trade.application.domain.filter.FindCommodityFilter.class);
        ValidatedCommodity validatedCommodity = mock(ValidatedCommodity.class);


        when(findCommodityFilterMapper.map(findCommodityFilter)).thenReturn(findCommodityFilterPersistence);
        when(mybatisValidatedCommodityRepository.findByFilter(findCommodityFilterPersistence)).thenReturn(List.of(validatedCommodityEntity));
        when(validatedCommodityEntityMapper.map(validatedCommodityEntity)).thenReturn(validatedCommodity);

        List<ValidatedCommodity> result = underTest.loadByFilter(findCommodityFilter);

        verify(findCommodityFilterMapper).map(findCommodityFilter);
        verify(mybatisValidatedCommodityRepository).findByFilter(findCommodityFilterPersistence);
        verify(validatedCommodityEntityMapper).map(validatedCommodityEntity);
        verifyNoMoreInteractions(findCommodityFilterMapper, mybatisValidatedCommodityRepository, validatedCommodityEntityMapper);

        assertThat(result, equalTo(List.of(validatedCommodity)));
    }
}
