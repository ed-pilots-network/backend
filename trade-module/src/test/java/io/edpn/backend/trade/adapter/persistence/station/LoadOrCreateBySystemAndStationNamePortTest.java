package io.edpn.backend.trade.adapter.persistence.station;

import io.edpn.backend.trade.adapter.persistence.StationRepository;
import io.edpn.backend.trade.adapter.persistence.entity.MybatisStationEntity;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.MybatisStationEntityMapper;
import io.edpn.backend.trade.adapter.persistence.filter.mapper.MybatisFindStationFilterMapper;
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
    private MybatisStationEntityMapper mybatisStationEntityMapper;

    @Mock
    private MybatisStationRepository mybatisStationRepository;

    @Mock
    private MybatisFindStationFilterMapper persistenceMybatisFindStationFilterMapper;

    private CreateOrLoadStationPort underTest;

    @BeforeEach
    public void setUp() {
        underTest = new StationRepository(mybatisStationEntityMapper, mybatisStationRepository, persistenceMybatisFindStationFilterMapper);
    }

    @Test
    void findOrCreateByNameNew() {
        Station inputStation = mock(Station.class);
        MybatisStationEntity inputMybatisStationEntity = mock(MybatisStationEntity.class);
        when(mybatisStationEntityMapper.map(inputStation)).thenReturn(inputMybatisStationEntity);

        MybatisStationEntity outputMybatisStationEntity = mock(MybatisStationEntity.class);
        Station expectedStation = mock(Station.class);
        when(mybatisStationEntityMapper.map(outputMybatisStationEntity)).thenReturn(expectedStation);

        when(mybatisStationRepository.createOrUpdateOnConflict(inputMybatisStationEntity)).thenReturn(outputMybatisStationEntity);

        Station result = underTest.createOrLoad(inputStation);
        assertThat(result, is(expectedStation));
    }
}
