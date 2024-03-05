package io.edpn.backend.trade.adapter.persistence.locatecommodity;

import io.edpn.backend.trade.adapter.persistence.LocateCommodityRepository;
import io.edpn.backend.trade.adapter.persistence.entity.LocateCommodityEntity;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.LocateCommodityEntityMapper;
import io.edpn.backend.trade.adapter.persistence.filter.LocateCommodityFilter;
import io.edpn.backend.trade.adapter.persistence.filter.mapper.LocateCommodityFilterMapper;
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
    private LocateCommodityEntityMapper locateCommodityEntityMapper;

    @Mock
    private LocateCommodityFilterMapper locateCommodityFilterMapper;

    private LocateCommodityByFilterPort underTest;

    @BeforeEach
    public void setUp() {
        underTest = new LocateCommodityRepository(mybatisLocateCommodityRepository, locateCommodityEntityMapper, locateCommodityFilterMapper);
    }

    @Test
    void locateCommodityByFilter() {
        //mock objects
        LocateCommodityFilter locateCommodityFilterPersistence = mock(LocateCommodityFilter.class);
        LocateCommodityEntity locateCommodityEntity = mock(LocateCommodityEntity.class);
        io.edpn.backend.trade.application.domain.filter.LocateCommodityFilter locateCommodityFilter = mock(io.edpn.backend.trade.application.domain.filter.LocateCommodityFilter.class);
        LocateCommodity locateCommodity = mock(LocateCommodity.class);

        when(locateCommodityFilterMapper.map(locateCommodityFilter)).thenReturn(locateCommodityFilterPersistence);
        when(mybatisLocateCommodityRepository.locateCommodityByFilter(locateCommodityFilterPersistence)).thenReturn(Collections.singletonList(locateCommodityEntity));
        when(locateCommodityEntityMapper.map(locateCommodityEntity)).thenReturn(locateCommodity);


        List<LocateCommodity> result = underTest.locateCommodityByFilter(locateCommodityFilter);

        verify(locateCommodityFilterMapper).map(locateCommodityFilter);
        verify(mybatisLocateCommodityRepository).locateCommodityByFilter(locateCommodityFilterPersistence);
        verify(locateCommodityEntityMapper).map(locateCommodityEntity);
        verifyNoMoreInteractions(locateCommodityFilterMapper, mybatisLocateCommodityRepository, locateCommodityEntityMapper);

        assertThat(result, equalTo(Collections.singletonList(locateCommodity)));

    }
}