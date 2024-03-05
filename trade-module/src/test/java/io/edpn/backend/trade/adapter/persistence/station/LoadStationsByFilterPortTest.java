package io.edpn.backend.trade.adapter.persistence.station;

import io.edpn.backend.trade.adapter.persistence.StationRepository;
import io.edpn.backend.trade.adapter.persistence.entity.StationEntity;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.StationEntityMapper;
import io.edpn.backend.trade.adapter.persistence.filter.FindStationFilter;
import io.edpn.backend.trade.adapter.persistence.filter.mapper.FindStationFilterMapper;
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
    private StationEntityMapper stationEntityMapper;

    @Mock
    private MybatisStationRepository mybatisStationRepository;

    @Mock
    private FindStationFilterMapper persistenceFindStationFilterMapper;

    private LoadStationsByFilterPort underTest;

    @BeforeEach
    void setUp() {
        underTest = new StationRepository(stationEntityMapper, mybatisStationRepository, persistenceFindStationFilterMapper);
    }

    @Test
    void testFindByFilter() {
        io.edpn.backend.trade.application.domain.filter.FindStationFilter findStationFilter = mock(io.edpn.backend.trade.application.domain.filter.FindStationFilter.class);
        FindStationFilter persistenceFindStationFilter = mock(FindStationFilter.class);
        StationEntity StationEntity = mock(io.edpn.backend.trade.adapter.persistence.entity.StationEntity.class);
        Station station = mock(Station.class);

        when(persistenceFindStationFilterMapper.map(findStationFilter)).thenReturn(persistenceFindStationFilter);
        when(mybatisStationRepository.findByFilter(persistenceFindStationFilter)).thenReturn(List.of(StationEntity));
        when(stationEntityMapper.map(StationEntity)).thenReturn(station);

        List<Station> result = underTest.loadByFilter(findStationFilter);

        assertThat(result, Matchers.containsInAnyOrder(station));
    }
}
