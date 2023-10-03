package io.edpn.backend.trade.adapter.persistence.station;

import io.edpn.backend.trade.adapter.persistence.StationRepository;
import io.edpn.backend.trade.adapter.persistence.entity.MybatisStationEntity;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisStationRepository;
import io.edpn.backend.trade.application.domain.Station;
import io.edpn.backend.trade.application.dto.persistence.entity.mapper.StationEntityMapper;
import io.edpn.backend.trade.application.dto.persistence.filter.mapper.PersistenceFindStationFilterMapper;
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
    private StationEntityMapper<MybatisStationEntity> mybatisStationEntityMapper;

    @Mock
    private MybatisStationRepository mybatisStationRepository;

    @Mock
    private PersistenceFindStationFilterMapper persistenceFindStationFilterMapper;

    private CreateOrLoadStationPort underTest;

    @BeforeEach
    public void setUp() {
        underTest = new StationRepository(mybatisStationEntityMapper, mybatisStationRepository, persistenceFindStationFilterMapper);
    }

    @Test
    void findOrCreateByNameNew() {
        Station inputStation = mock(Station.class);
        MybatisStationEntity inputStationEntity = mock(MybatisStationEntity.class);
        when(mybatisStationEntityMapper.map(inputStation)).thenReturn(inputStationEntity);

        MybatisStationEntity outputStationEntity = mock(MybatisStationEntity.class);
        Station expectedStation = mock(Station.class);
        when(mybatisStationEntityMapper.map(outputStationEntity)).thenReturn(expectedStation);

        when(mybatisStationRepository.createOrUpdateOnConflict(inputStationEntity)).thenReturn(outputStationEntity);

        Station result = underTest.createOrLoad(inputStation);
        assertThat(result, is(expectedStation));
    }
}
