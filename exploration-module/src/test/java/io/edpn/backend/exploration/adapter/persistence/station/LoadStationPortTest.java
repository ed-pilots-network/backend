package io.edpn.backend.exploration.adapter.persistence.station;

import io.edpn.backend.exploration.adapter.persistence.MybatisStationRepository;
import io.edpn.backend.exploration.adapter.persistence.StationRepository;
import io.edpn.backend.exploration.adapter.persistence.entity.MybatisStationEntity;
import io.edpn.backend.exploration.application.domain.Station;
import io.edpn.backend.exploration.application.dto.persistence.entity.mapper.StationEntityMapper;
import io.edpn.backend.exploration.application.port.outgoing.station.LoadStationPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoadStationPortTest {
    @Mock
    private MybatisStationRepository mybatisStationRepository;

    @Mock
    private StationEntityMapper<MybatisStationEntity> systemEntityMapper;

    private LoadStationPort underTest;

    @BeforeEach
    void setUp() {
        underTest = new StationRepository(mybatisStationRepository, systemEntityMapper);
    }

    @Test
    void load_shouldFindByNameAndMap() {

        String systemName = "system";
        String stationName = "station";
        MybatisStationEntity entity = mock(MybatisStationEntity.class);
        Station mapped = mock(Station.class);
        when(mybatisStationRepository.findByIdentifier(systemName, stationName)).thenReturn(Optional.of(entity));
        when(systemEntityMapper.map(entity)).thenReturn(mapped);


        Optional<Station> result = underTest.load(systemName, stationName);


        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(mapped));
    }
}