package io.edpn.backend.trade.adapter.persistence.station;

import io.edpn.backend.trade.adapter.persistence.StationRepository;
import io.edpn.backend.trade.adapter.persistence.entity.MybatisStationEntity;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisMarketDatumRepository;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisStationRepository;
import io.edpn.backend.trade.application.domain.Station;
import io.edpn.backend.trade.application.domain.filter.FindStationFilter;
import io.edpn.backend.trade.application.dto.persistence.entity.mapper.StationEntityMapper;
import io.edpn.backend.trade.application.dto.persistence.filter.PersistenceFindStationFilter;
import io.edpn.backend.trade.application.dto.persistence.filter.mapper.PersistenceFindStationFilterMapper;
import io.edpn.backend.trade.application.port.outgoing.station.LoadStationsByFilterPort;
import io.edpn.backend.util.IdGenerator;
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
    private IdGenerator idGenerator;

    @Mock
    private StationEntityMapper<MybatisStationEntity> mybatisStationEntityMapper;

    @Mock
    private MybatisStationRepository mybatisStationRepository;

    @Mock
    private MybatisMarketDatumRepository mybatisMarketDatumRepository;

    @Mock
    private PersistenceFindStationFilterMapper persistenceFindStationFilterMapper;

    private LoadStationsByFilterPort underTest;

    @BeforeEach
    void setUp() {
        underTest = new StationRepository(idGenerator, mybatisStationEntityMapper, mybatisStationRepository, mybatisMarketDatumRepository, persistenceFindStationFilterMapper);
    }

    @Test
    void testFindByFilter() {
        FindStationFilter findStationFilter = mock(FindStationFilter.class);
        PersistenceFindStationFilter persistenceFindStationFilter = mock(PersistenceFindStationFilter.class);
        MybatisStationEntity StationEntity = mock(MybatisStationEntity.class);
        Station station = mock(Station.class);

        when(persistenceFindStationFilterMapper.map(findStationFilter)).thenReturn(persistenceFindStationFilter);
        when(mybatisStationRepository.findByFilter(persistenceFindStationFilter)).thenReturn(List.of(StationEntity));
        when(mybatisStationEntityMapper.map(StationEntity)).thenReturn(station);

        List<Station> result = underTest.loadByFilter(findStationFilter);

        assertThat(result, Matchers.containsInAnyOrder(station));
    }
}
