package io.edpn.backend.trade.adapter.persistence.station;

import io.edpn.backend.trade.adapter.persistence.StationRepository;
import io.edpn.backend.trade.adapter.persistence.entity.MybatisStationEntity;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisStationRepository;
import io.edpn.backend.trade.application.domain.Station;
import io.edpn.backend.trade.application.dto.persistence.entity.mapper.StationEntityMapper;
import io.edpn.backend.trade.application.dto.persistence.filter.mapper.PersistenceFindStationFilterMapper;
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
    private StationEntityMapper<MybatisStationEntity> mybatisStationEntityMapper;
    @Mock
    private MybatisStationRepository mybatisStationRepository;
    @Mock
    private PersistenceFindStationFilterMapper mybatisPersistenceFindStationFilterMapper;
    private UpdateStationPort underTest;

    @BeforeEach
    public void setUp() {
        underTest = new StationRepository(mybatisStationEntityMapper, mybatisStationRepository, mybatisPersistenceFindStationFilterMapper);
    }

    @Test
    void testUpdateWhenStationIsFoundAfterUpdate() {
        Station inputStation = mock(Station.class);
        MybatisStationEntity inputStationEntity = mock(MybatisStationEntity.class);
        MybatisStationEntity resultStationEntity = mock(MybatisStationEntity.class);
        Station expectedStation = mock(Station.class);

        when(mybatisStationEntityMapper.map(inputStation)).thenReturn(inputStationEntity);
        UUID uuid = UUID.randomUUID();
        when(inputStationEntity.getId()).thenReturn(uuid);
        when(mybatisStationRepository.findById(uuid)).thenReturn(Optional.of(resultStationEntity));
        when(mybatisStationEntityMapper.map(resultStationEntity)).thenReturn(expectedStation);

        Station result = underTest.update(inputStation);

        assertThat(result, is(expectedStation));
        verify(mybatisStationEntityMapper, times(1)).map(inputStation);
        verify(mybatisStationRepository, times(1)).update(inputStationEntity);
    }

    @Test
    void testUpdateWhenStationIsNotFoundAfterUpdate() {
        Station inputStation = mock(Station.class);
        MybatisStationEntity inputStationEntity = mock(MybatisStationEntity.class);

        when(mybatisStationEntityMapper.map(inputStation)).thenReturn(inputStationEntity);
        when(inputStationEntity.getId()).thenReturn(UUID.randomUUID());
        when(mybatisStationRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        DatabaseEntityNotFoundException exception = assertThrows(DatabaseEntityNotFoundException.class, () -> underTest.update(inputStation));

        assertThat(exception.getMessage(), equalTo("station with id: " + inputStationEntity.getId() + " could not be found after update"));
        verify(mybatisStationEntityMapper, times(1)).map(inputStation);
        verify(mybatisStationRepository, times(1)).update(inputStationEntity);
    }

}
