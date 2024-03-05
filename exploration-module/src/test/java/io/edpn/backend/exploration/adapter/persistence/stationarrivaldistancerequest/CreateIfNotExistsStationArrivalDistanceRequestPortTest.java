package io.edpn.backend.exploration.adapter.persistence.stationarrivaldistancerequest;

import io.edpn.backend.exploration.adapter.persistence.MybatisStationArrivalDistanceRequestRepository;
import io.edpn.backend.exploration.adapter.persistence.StationArrivalDistanceRequestRepository;
import io.edpn.backend.exploration.adapter.persistence.entity.MybatisStationArrivalDistanceRequestEntity;
import io.edpn.backend.exploration.adapter.persistence.entity.mapper.MybatisStationArrivalDistanceRequestEntityMapper;
import io.edpn.backend.exploration.application.domain.StationArrivalDistanceRequest;
import io.edpn.backend.exploration.application.port.outgoing.stationarrivaldistancerequest.CreateIfNotExistsStationArrivalDistanceRequestPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateIfNotExistsStationArrivalDistanceRequestPortTest {

    @Mock
    private MybatisStationArrivalDistanceRequestRepository mybatisStationArrivalDistanceRequestRepository;

    @Mock
    private MybatisStationArrivalDistanceRequestEntityMapper mybatisStationArrivalDistanceRequestEntityMapper;

    private CreateIfNotExistsStationArrivalDistanceRequestPort underTest;

    @BeforeEach
    void setUp() {
        underTest = new StationArrivalDistanceRequestRepository(mybatisStationArrivalDistanceRequestRepository, mybatisStationArrivalDistanceRequestEntityMapper);
    }

    @Test
    void create_shouldMapAndInsert() {
        StationArrivalDistanceRequest request = mock(StationArrivalDistanceRequest.class);
        MybatisStationArrivalDistanceRequestEntity entity = mock(MybatisStationArrivalDistanceRequestEntity.class);
        when(mybatisStationArrivalDistanceRequestEntityMapper.map(request)).thenReturn(entity);


        underTest.createIfNotExists(request);


        verify(mybatisStationArrivalDistanceRequestRepository).insertIfNotExists(entity);
    }
}