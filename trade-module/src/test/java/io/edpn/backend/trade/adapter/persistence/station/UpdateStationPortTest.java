package io.edpn.backend.trade.adapter.persistence.station;

import io.edpn.backend.trade.adapter.persistence.StationRepository;
import io.edpn.backend.trade.adapter.persistence.entity.MybatisStationEntity;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.MybatisStationEntityMapper;
import io.edpn.backend.trade.adapter.persistence.filter.mapper.MybatisFindStationFilterMapper;
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
    private MybatisStationEntityMapper mybatisStationEntityMapper;
    @Mock
    private MybatisStationRepository mybatisStationRepository;
    @Mock
    private MybatisFindStationFilterMapper mybatisFindStationFilterMapper;
    private UpdateStationPort underTest;

    @BeforeEach
    public void setUp() {
        underTest = new StationRepository(mybatisStationEntityMapper, mybatisStationRepository, mybatisFindStationFilterMapper);
    }

    @Test
    void testUpdateWhenStationIsFoundAfterUpdate() {
        Station inputStation = mock(Station.class);
        MybatisStationEntity inputMybatisStationEntity = mock(MybatisStationEntity.class);
        MybatisStationEntity resultMybatisStationEntity = mock(MybatisStationEntity.class);
        Station expectedStation = mock(Station.class);

        when(mybatisStationEntityMapper.map(inputStation)).thenReturn(inputMybatisStationEntity);
        UUID uuid = UUID.randomUUID();
        when(inputMybatisStationEntity.getId()).thenReturn(uuid);
        when(mybatisStationRepository.findById(uuid)).thenReturn(Optional.of(resultMybatisStationEntity));
        when(mybatisStationEntityMapper.map(resultMybatisStationEntity)).thenReturn(expectedStation);

        Station result = underTest.update(inputStation);

        assertThat(result, is(expectedStation));
        verify(mybatisStationEntityMapper, times(1)).map(inputStation);
        verify(mybatisStationRepository, times(1)).update(inputMybatisStationEntity);
    }

    @Test
    void testUpdateWhenStationIsNotFoundAfterUpdate() {
        Station inputStation = mock(Station.class);
        MybatisStationEntity inputMybatisStationEntity = mock(MybatisStationEntity.class);

        when(mybatisStationEntityMapper.map(inputStation)).thenReturn(inputMybatisStationEntity);
        when(inputMybatisStationEntity.getId()).thenReturn(UUID.randomUUID());
        when(mybatisStationRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        DatabaseEntityNotFoundException exception = assertThrows(DatabaseEntityNotFoundException.class, () -> underTest.update(inputStation));

        assertThat(exception.getMessage(), equalTo("station with id: " + inputMybatisStationEntity.getId() + " could not be found after update"));
        verify(mybatisStationEntityMapper, times(1)).map(inputStation);
        verify(mybatisStationRepository, times(1)).update(inputMybatisStationEntity);
    }

}
