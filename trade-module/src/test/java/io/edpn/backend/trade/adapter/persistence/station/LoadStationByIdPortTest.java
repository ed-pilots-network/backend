package io.edpn.backend.trade.adapter.persistence.station;

import io.edpn.backend.trade.adapter.persistence.StationRepository;
import io.edpn.backend.trade.adapter.persistence.entity.StationEntity;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.StationEntityMapper;
import io.edpn.backend.trade.adapter.persistence.filter.mapper.FindStationFilterMapper;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisMarketDatumRepository;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisStationRepository;
import io.edpn.backend.trade.application.domain.Station;
import io.edpn.backend.trade.application.port.outgoing.station.LoadStationByIdPort;
import io.edpn.backend.util.IdGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoadStationByIdPortTest {

    @Mock
    private IdGenerator idGenerator;

    @Mock
    private StationEntityMapper stationEntityMapper;

    @Mock
    private MybatisStationRepository mybatisStationRepository;

    @Mock
    private MybatisMarketDatumRepository mybatisMarketDatumRepository;

    @Mock
    private FindStationFilterMapper persistenceFindStationFilterMapper;

    private LoadStationByIdPort underTest;

    @BeforeEach
    public void setUp() {
        underTest = new StationRepository(stationEntityMapper, mybatisStationRepository, persistenceFindStationFilterMapper);
    }

    @Test
    void findById() {
        UUID id = UUID.randomUUID();
        StationEntity mockStationEntity = mock(StationEntity.class);
        Station mockStation = mock(Station.class);

        when(mybatisStationRepository.findById(id)).thenReturn(Optional.of(mockStationEntity));
        when(stationEntityMapper.map(mockStationEntity)).thenReturn(mockStation);

        Optional<Station> results = underTest.loadById(id);

        verify(mybatisStationRepository).findById(id);
        verify(stationEntityMapper).map(mockStationEntity);
        verifyNoMoreInteractions(mybatisStationRepository, stationEntityMapper, idGenerator, mybatisMarketDatumRepository);

        assertThat(results.isPresent(), is(true));
        assertThat(results.get(), equalTo(mockStation));
    }

    @Test
    void findByIdNotFound() {
        UUID id = UUID.randomUUID();

        when(mybatisStationRepository.findById(id)).thenReturn(Optional.empty());
        Optional<Station> result = underTest.loadById(id);

        verify(mybatisStationRepository).findById(id);
        verify(stationEntityMapper, never()).map(any(StationEntity.class));
        verifyNoMoreInteractions(mybatisStationRepository, stationEntityMapper, idGenerator, mybatisMarketDatumRepository);

        assertThat(result, equalTo(Optional.empty()));
    }
}
