package io.edpn.backend.trade.adapter.persistence.station;

import io.edpn.backend.trade.adapter.persistence.StationRepository;
import io.edpn.backend.trade.adapter.persistence.entity.StationEntity;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.StationEntityMapper;
import io.edpn.backend.trade.adapter.persistence.filter.mapper.FindStationFilterMapper;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisStationRepository;
import io.edpn.backend.trade.application.domain.Station;
import io.edpn.backend.trade.application.port.outgoing.station.CreateOrLoadStationPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoadOrCreateBySystemAndStationNamePortTest {

    @Mock
    private StationEntityMapper stationEntityMapper;

    @Mock
    private MybatisStationRepository mybatisStationRepository;

    @Mock
    private FindStationFilterMapper persistenceFindStationFilterMapper;

    private CreateOrLoadStationPort underTest;

    @BeforeEach
    public void setUp() {
        underTest = new StationRepository(stationEntityMapper, mybatisStationRepository, persistenceFindStationFilterMapper);
    }

    @Test
    void findOrCreateByNameNew() {
        Station inputStation = mock(Station.class);
        StationEntity inputStationEntity = mock(StationEntity.class);
        when(stationEntityMapper.map(inputStation)).thenReturn(inputStationEntity);

        StationEntity outputStationEntity = mock(StationEntity.class);
        Station expectedStation = mock(Station.class);
        when(stationEntityMapper.map(outputStationEntity)).thenReturn(expectedStation);

        when(mybatisStationRepository.createOrUpdateOnConflict(inputStationEntity)).thenReturn(outputStationEntity);

        Station result = underTest.createOrLoad(inputStation);
        assertThat(result, is(expectedStation));
    }
}
