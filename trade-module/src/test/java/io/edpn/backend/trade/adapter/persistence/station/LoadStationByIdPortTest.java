package io.edpn.backend.trade.adapter.persistence.station;

import io.edpn.backend.trade.adapter.persistence.StationRepository;
import io.edpn.backend.trade.adapter.persistence.entity.MybatisStationEntity;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.MybatisStationEntityMapper;
import io.edpn.backend.trade.adapter.persistence.filter.mapper.MybatisFindStationFilterMapper;
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
    private MybatisStationEntityMapper mybatisStationEntityMapper;

    @Mock
    private MybatisStationRepository mybatisStationRepository;

    @Mock
    private MybatisMarketDatumRepository mybatisMarketDatumRepository;

    @Mock
    private MybatisFindStationFilterMapper persistenceMybatisFindStationFilterMapper;

    private LoadStationByIdPort underTest;

    @BeforeEach
    public void setUp() {
        underTest = new StationRepository(mybatisStationEntityMapper, mybatisStationRepository, persistenceMybatisFindStationFilterMapper);
    }

    @Test
    void findById() {
        UUID id = UUID.randomUUID();
        MybatisStationEntity mockMybatisStationEntity = mock(MybatisStationEntity.class);
        Station mockStation = mock(Station.class);

        when(mybatisStationRepository.findById(id)).thenReturn(Optional.of(mockMybatisStationEntity));
        when(mybatisStationEntityMapper.map(mockMybatisStationEntity)).thenReturn(mockStation);

        Optional<Station> results = underTest.loadById(id);

        verify(mybatisStationRepository).findById(id);
        verify(mybatisStationEntityMapper).map(mockMybatisStationEntity);
        verifyNoMoreInteractions(mybatisStationRepository, mybatisStationEntityMapper, idGenerator, mybatisMarketDatumRepository);

        assertThat(results.isPresent(), is(true));
        assertThat(results.get(), equalTo(mockStation));
    }

    @Test
    void findByIdNotFound() {
        UUID id = UUID.randomUUID();

        when(mybatisStationRepository.findById(id)).thenReturn(Optional.empty());
        Optional<Station> result = underTest.loadById(id);

        verify(mybatisStationRepository).findById(id);
        verify(mybatisStationEntityMapper, never()).map(any(MybatisStationEntity.class));
        verifyNoMoreInteractions(mybatisStationRepository, mybatisStationEntityMapper, idGenerator, mybatisMarketDatumRepository);

        assertThat(result, equalTo(Optional.empty()));
    }
}
