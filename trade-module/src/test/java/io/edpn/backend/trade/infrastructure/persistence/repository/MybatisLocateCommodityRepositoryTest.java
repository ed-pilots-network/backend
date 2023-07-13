package io.edpn.backend.trade.infrastructure.persistence.repository;

import io.edpn.backend.trade.domain.filter.v1.LocateCommodityFilter;
import io.edpn.backend.trade.domain.model.LocateCommodity;
import io.edpn.backend.trade.domain.repository.LocateCommodityRepository;
import io.edpn.backend.trade.infrastructure.persistence.entity.LocateCommodityEntity;
import io.edpn.backend.trade.infrastructure.persistence.filter.LocateCommodityFilterPersistence;
import io.edpn.backend.trade.infrastructure.persistence.mappers.entity.LocateCommodityMapper;
import io.edpn.backend.trade.infrastructure.persistence.mappers.filter.LocateCommodityFilterMapper;
import io.edpn.backend.trade.infrastructure.persistence.mappers.mybatis.LocateCommodityEntityMapper;
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
class MybatisLocateCommodityRepositoryTest {

    @Mock
    private LocateCommodityMapper locateCommodityMapper;
    @Mock
    private LocateCommodityEntityMapper locateCommodityEntityMapper;
    @Mock
    private LocateCommodityFilterMapper locateCommodityFilterMapper;

    private LocateCommodityRepository underTest;

    @BeforeEach
    void setUp() {
        underTest = new MybatisLocateCommodityRepository(locateCommodityMapper, locateCommodityEntityMapper, locateCommodityFilterMapper);
    }

    @Test
    void locateCommodityByFilter() {
        //mock objects
        LocateCommodityFilterPersistence locateCommodityFilterPersistence = mock(LocateCommodityFilterPersistence.class);
        LocateCommodityEntity locateCommodityEntity = mock(LocateCommodityEntity.class);
        LocateCommodityFilter locateCommodityFilter = mock(LocateCommodityFilter.class);
        LocateCommodity locateCommodity = mock(LocateCommodity.class);

        when(locateCommodityFilterMapper.map(locateCommodityFilter)).thenReturn(locateCommodityFilterPersistence);
        when(locateCommodityEntityMapper.locateCommodityByFilter(locateCommodityFilterPersistence)).thenReturn(Collections.singletonList(locateCommodityEntity));
        when(locateCommodityMapper.map(locateCommodityEntity)).thenReturn(locateCommodity);



        List<LocateCommodity> result = underTest.locateCommodityByFilter(locateCommodityFilter);

        verify(locateCommodityFilterMapper).map(locateCommodityFilter);
        verify(locateCommodityEntityMapper).locateCommodityByFilter(locateCommodityFilterPersistence);
        verify(locateCommodityMapper).map(locateCommodityEntity);
        verifyNoMoreInteractions(locateCommodityFilterMapper, locateCommodityEntityMapper, locateCommodityMapper);

        assertThat(result, equalTo(Collections.singletonList(locateCommodity)));


    }
}