package io.edpn.backend.trade.adapter.persistence.locatecommodity;

import io.edpn.backend.trade.adapter.persistence.LocateCommodityRepository;
import io.edpn.backend.trade.adapter.persistence.entity.MybatisLocateCommodityEntity;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.MybatisLocateCommodityEntityMapper;
import io.edpn.backend.trade.adapter.persistence.filter.MybatisLocateCommodityFilter;
import io.edpn.backend.trade.adapter.persistence.filter.mapper.MybatisLocateCommodityFilterMapper;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisLocateCommodityRepository;
import io.edpn.backend.trade.application.domain.LocateCommodity;
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
    private MybatisLocateCommodityEntityMapper mybatisLocateCommodityEntityMapper;

    @Mock
    private MybatisLocateCommodityFilterMapper mybatisLocateCommodityFilterMapper;

    private LocateCommodityByFilterPort underTest;

    @BeforeEach
    public void setUp() {
        underTest = new LocateCommodityRepository(mybatisLocateCommodityRepository, mybatisLocateCommodityEntityMapper, mybatisLocateCommodityFilterMapper);
    }

    @Test
    void locateCommodityByFilter() {
        //mock objects
        MybatisLocateCommodityFilter mybatisLocateCommodityFilterPersistence = mock(MybatisLocateCommodityFilter.class);
        MybatisLocateCommodityEntity mybatisLocateCommodityEntity = mock(MybatisLocateCommodityEntity.class);
        io.edpn.backend.trade.application.domain.filter.LocateCommodityFilter locateCommodityFilter = mock(io.edpn.backend.trade.application.domain.filter.LocateCommodityFilter.class);
        LocateCommodity locateCommodity = mock(LocateCommodity.class);

        when(mybatisLocateCommodityFilterMapper.map(locateCommodityFilter)).thenReturn(mybatisLocateCommodityFilterPersistence);
        when(mybatisLocateCommodityRepository.locateCommodityByFilter(mybatisLocateCommodityFilterPersistence)).thenReturn(Collections.singletonList(mybatisLocateCommodityEntity));
        when(mybatisLocateCommodityEntityMapper.map(mybatisLocateCommodityEntity)).thenReturn(locateCommodity);


        List<LocateCommodity> result = underTest.locateCommodityByFilter(locateCommodityFilter);

        verify(mybatisLocateCommodityFilterMapper).map(locateCommodityFilter);
        verify(mybatisLocateCommodityRepository).locateCommodityByFilter(mybatisLocateCommodityFilterPersistence);
        verify(mybatisLocateCommodityEntityMapper).map(mybatisLocateCommodityEntity);
        verifyNoMoreInteractions(mybatisLocateCommodityFilterMapper, mybatisLocateCommodityRepository, mybatisLocateCommodityEntityMapper);

        assertThat(result, equalTo(Collections.singletonList(locateCommodity)));

    }
}