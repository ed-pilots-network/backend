package io.edpn.backend.trade.adapter.persistence.locatecommodity;

import io.edpn.backend.trade.adapter.persistence.LocateCommodityRepository;
import io.edpn.backend.trade.adapter.persistence.entity.MybatisLocateCommodityEntity;
import io.edpn.backend.trade.adapter.persistence.filter.MybatisLocateCommodityFilter;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisLocateCommodityRepository;
import io.edpn.backend.trade.application.domain.LocateCommodity;
import io.edpn.backend.trade.application.domain.PageInfo;
import io.edpn.backend.trade.application.domain.PagedResult;
import io.edpn.backend.trade.application.domain.filter.LocateCommodityFilter;
import io.edpn.backend.trade.application.dto.persistence.entity.mapper.LocateCommodityEntityMapper;
import io.edpn.backend.trade.application.dto.persistence.entity.mapper.PersistencePageInfoMapper;
import io.edpn.backend.trade.application.dto.persistence.filter.mapper.PersistenceLocateCommodityFilterMapper;
import io.edpn.backend.trade.application.port.outgoing.locatecommodity.LocateCommodityByFilterPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LocateCommodityByFilterPortTest {

    @Mock
    private MybatisLocateCommodityRepository mybatisLocateCommodityRepository;

    @Mock
    private LocateCommodityEntityMapper<MybatisLocateCommodityEntity> mybatisLocateCommodityEntityMapper;

    @Mock
    private PersistenceLocateCommodityFilterMapper persistenceLocateCommodityFilterMapper;

    @Mock
    private PersistencePageInfoMapper persistencePageInfoMapper;

    private LocateCommodityByFilterPort underTest;

    @BeforeEach
    public void setUp() {
        underTest = new LocateCommodityRepository(mybatisLocateCommodityRepository, mybatisLocateCommodityEntityMapper, persistenceLocateCommodityFilterMapper, persistencePageInfoMapper);
    }

    @Test
    void locateCommodityByFilter() {
        //mock objects
        MybatisLocateCommodityFilter locateCommodityFilterPersistence = mock(MybatisLocateCommodityFilter.class);
        MybatisLocateCommodityEntity locateCommodityEntity = mock(MybatisLocateCommodityEntity.class);
        LocateCommodityFilter locateCommodityFilter = mock(LocateCommodityFilter.class);
        LocateCommodity locateCommodity = mock(LocateCommodity.class);
        PageInfo pageInfo = mock(PageInfo.class);

        when(persistenceLocateCommodityFilterMapper.map(locateCommodityFilter)).thenReturn(locateCommodityFilterPersistence);
        when(mybatisLocateCommodityRepository.locateCommodityByFilter(locateCommodityFilterPersistence)).thenReturn(Collections.singletonList(locateCommodityEntity));
        when(mybatisLocateCommodityEntityMapper.map(locateCommodityEntity)).thenReturn(locateCommodity);
        when(persistencePageInfoMapper.map(locateCommodityEntity)).thenReturn(pageInfo);

        PagedResult<LocateCommodity> result = underTest.locateCommodityByFilter(locateCommodityFilter);

        assertThat(result, notNullValue());
        assertThat(result.pageInfo(), is(pageInfo));
        assertThat(result.result(), equalTo(Collections.singletonList(locateCommodity)));

    }
}