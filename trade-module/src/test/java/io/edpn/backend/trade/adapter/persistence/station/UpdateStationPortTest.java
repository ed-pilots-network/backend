package io.edpn.backend.trade.adapter.persistence.station;

import io.edpn.backend.trade.adapter.persistence.StationRepository;
import io.edpn.backend.trade.adapter.persistence.entity.StationEntity;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.StationEntityMapper;
import io.edpn.backend.trade.adapter.persistence.filter.mapper.FindStationFilterMapper;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisStationRepository;
import io.edpn.backend.trade.application.domain.Station;
import io.edpn.backend.trade.application.port.outgoing.station.UpdateStationPort;
import io.edpn.backend.util.exception.DatabaseEntityNotFoundException;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UpdateStationPortTest {

    @Mock
    private StationEntityMapper stationEntityMapper;
    @Mock
    private MybatisStationRepository mybatisStationRepository;
    @Mock
    private FindStationFilterMapper findStationFilterMapper;
    private UpdateStationPort underTest;

    @BeforeEach
    public void setUp() {
        underTest = new StationRepository(stationEntityMapper, mybatisStationRepository, findStationFilterMapper);
    }

    @Test
    void testUpdateWhenStationIsFoundAfterUpdate() {
        Station inputStation = mock(Station.class);
        StationEntity inputStationEntity = mock(StationEntity.class);
        StationEntity resultStationEntity = mock(StationEntity.class);
        Station expectedStation = mock(Station.class);

        when(stationEntityMapper.map(inputStation)).thenReturn(inputStationEntity);
        UUID uuid = UUID.randomUUID();
        when(inputStationEntity.getId()).thenReturn(uuid);
        when(mybatisStationRepository.findById(uuid)).thenReturn(Optional.of(resultStationEntity));
        when(stationEntityMapper.map(resultStationEntity)).thenReturn(expectedStation);

        Station result = underTest.update(inputStation);

        assertThat(result, is(expectedStation));
        verify(stationEntityMapper, times(1)).map(inputStation);
        verify(mybatisStationRepository, times(1)).update(inputStationEntity);
    }

    @Test
    void testUpdateWhenStationIsNotFoundAfterUpdate() {
        Station inputStation = mock(Station.class);
        StationEntity inputStationEntity = mock(StationEntity.class);

        when(stationEntityMapper.map(inputStation)).thenReturn(inputStationEntity);
        when(inputStationEntity.getId()).thenReturn(UUID.randomUUID());
        when(mybatisStationRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        DatabaseEntityNotFoundException exception = assertThrows(DatabaseEntityNotFoundException.class, () -> underTest.update(inputStation));

        assertThat(exception.getMessage(), equalTo("station with id: " + inputStationEntity.getId() + " could not be found after update"));
        verify(stationEntityMapper, times(1)).map(inputStation);
        verify(mybatisStationRepository, times(1)).update(inputStationEntity);
    }

}
