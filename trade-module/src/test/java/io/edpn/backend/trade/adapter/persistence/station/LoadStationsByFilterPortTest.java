package io.edpn.backend.trade.adapter.persistence.station;

import io.edpn.backend.trade.adapter.persistence.StationRepository;
import io.edpn.backend.trade.adapter.persistence.entity.MybatisStationEntity;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.MybatisStationEntityMapper;
import io.edpn.backend.trade.adapter.persistence.filter.MybatisFindStationFilter;
import io.edpn.backend.trade.adapter.persistence.filter.mapper.MybatisFindStationFilterMapper;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisStationRepository;
import io.edpn.backend.trade.application.domain.Station;
import io.edpn.backend.trade.application.port.outgoing.station.LoadStationsByFilterPort;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoadStationsByFilterPortTest {

    @Mock
    private MybatisStationEntityMapper mybatisStationEntityMapper;

    @Mock
    private MybatisStationRepository mybatisStationRepository;

    @Mock
    private MybatisFindStationFilterMapper persistenceMybatisFindStationFilterMapper;

    private LoadStationsByFilterPort underTest;

    @BeforeEach
    void setUp() {
        underTest = new StationRepository(mybatisStationEntityMapper, mybatisStationRepository, persistenceMybatisFindStationFilterMapper);
    }

    @Test
    void testFindByFilter() {
        io.edpn.backend.trade.application.domain.filter.FindStationFilter findStationFilter = mock(io.edpn.backend.trade.application.domain.filter.FindStationFilter.class);
        MybatisFindStationFilter mybatisFindStationFilter = mock(MybatisFindStationFilter.class);
        MybatisStationEntity MybatisStationEntity = mock(MybatisStationEntity.class);
        Station station = mock(Station.class);

        when(persistenceMybatisFindStationFilterMapper.map(findStationFilter)).thenReturn(mybatisFindStationFilter);
        when(mybatisStationRepository.findByFilter(mybatisFindStationFilter)).thenReturn(List.of(MybatisStationEntity));
        when(mybatisStationEntityMapper.map(MybatisStationEntity)).thenReturn(station);

        List<Station> result = underTest.loadByFilter(findStationFilter);

        assertThat(result, Matchers.containsInAnyOrder(station));
    }
}
