package io.edpn.backend.exploration.adapter.persistence.station;

import io.edpn.backend.exploration.adapter.persistence.MybatisStationRepository;
import io.edpn.backend.exploration.adapter.persistence.StationRepository;
import io.edpn.backend.exploration.adapter.persistence.entity.MybatisStationEntity;
import io.edpn.backend.exploration.adapter.persistence.entity.mapper.MybatisStationEntityMapper;
import io.edpn.backend.exploration.application.domain.Station;
import io.edpn.backend.exploration.application.port.outgoing.station.SaveOrUpdateStationPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class SaveOrUpdateStationPortTest {
    @Mock
    private MybatisStationRepository mybatisStationRepository;

    @Mock
    private MybatisStationEntityMapper starEntityMapper;

    private SaveOrUpdateStationPort underTest;

    @BeforeEach
    void setUp() {
        underTest = new StationRepository(mybatisStationRepository, starEntityMapper);
    }

    @Test
    void save_shouldUpdateAndLoad() {

        Station star = mock(Station.class);
        MybatisStationEntity entity = mock(MybatisStationEntity.class);
        Station loaded = mock(Station.class);
        when(starEntityMapper.map(star)).thenReturn(entity);
        when(mybatisStationRepository.insertOrUpdateOnConflict(any())).thenReturn(entity);
        when(starEntityMapper.map(entity)).thenReturn(loaded);


        Station result = underTest.saveOrUpdate(star);


        assertThat(result, is(loaded));
    }
}
