package io.edpn.backend.exploration.adapter.persistence.stationarrivaldistancerequest;

import io.edpn.backend.exploration.adapter.persistence.MybatisStationArrivalDistanceRequestRepository;
import io.edpn.backend.exploration.adapter.persistence.StationArrivalDistanceRequestRepository;
import io.edpn.backend.exploration.adapter.persistence.entity.MybatisStationArrivalDistanceRequestEntity;
import io.edpn.backend.exploration.adapter.persistence.entity.mapper.MybatisStationArrivalDistanceRequestEntityMapper;
import io.edpn.backend.exploration.application.port.outgoing.stationarrivaldistancerequest.DeleteStationArrivalDistanceRequestPort;
import io.edpn.backend.util.Module;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeleteStationArrivalDistanceRequestPortTest {

    @Mock
    private MybatisStationArrivalDistanceRequestRepository mybatisStationArrivalDistanceRequestRepository;

    @Mock
    private MybatisStationArrivalDistanceRequestEntityMapper mybatisStationArrivalDistanceRequestEntityMapper;

    private DeleteStationArrivalDistanceRequestPort underTest;

    @BeforeEach
    void setUp() {
        underTest = new StationArrivalDistanceRequestRepository(mybatisStationArrivalDistanceRequestRepository, mybatisStationArrivalDistanceRequestEntityMapper);
    }

    @Test
    void delete_shouldDeleteFromRepository() {

        String systemName = "system";
        String stationName = "station";
        Module requestingModule = mock(Module.class);


        underTest.delete(systemName, stationName, requestingModule);


        verify(mybatisStationArrivalDistanceRequestRepository).delete(eq(new MybatisStationArrivalDistanceRequestEntity(systemName, stationName, requestingModule)));
    }
}