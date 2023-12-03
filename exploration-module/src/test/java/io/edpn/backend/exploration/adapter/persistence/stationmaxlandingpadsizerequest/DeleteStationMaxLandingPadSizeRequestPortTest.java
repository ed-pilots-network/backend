package io.edpn.backend.exploration.adapter.persistence.stationmaxlandingpadsizerequest;

import io.edpn.backend.exploration.adapter.persistence.MybatisStationMaxLandingPadSizeRequestRepository;
import io.edpn.backend.exploration.adapter.persistence.StationMaxLandingPadSizeRequestRepository;
import io.edpn.backend.exploration.adapter.persistence.entity.MybatisStationMaxLandingPadSizeRequestEntity;
import io.edpn.backend.exploration.adapter.persistence.entity.mapper.MybatisStationMaxLandingPadSizeRequestEntityMapper;
import io.edpn.backend.exploration.application.port.outgoing.stationmaxlandingpadsizerequest.DeleteStationMaxLandingPadSizeRequestPort;
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
class DeleteStationMaxLandingPadSizeRequestPortTest {

    @Mock
    private MybatisStationMaxLandingPadSizeRequestRepository mybatisStationMaxLandingPadSizeRequestRepository;

    @Mock
    private MybatisStationMaxLandingPadSizeRequestEntityMapper mybatisStationMaxLandingPadSizeRequestEntityMapper;

    private DeleteStationMaxLandingPadSizeRequestPort underTest;

    @BeforeEach
    void setUp() {
        underTest = new StationMaxLandingPadSizeRequestRepository(mybatisStationMaxLandingPadSizeRequestRepository, mybatisStationMaxLandingPadSizeRequestEntityMapper);
    }

    @Test
    void delete_shouldDeleteFromRepository() {

        String systemName = "system";
        String stationName = "station";
        Module requestingModule = mock(Module.class);


        underTest.delete(systemName, stationName, requestingModule);


        verify(mybatisStationMaxLandingPadSizeRequestRepository).delete(eq(new MybatisStationMaxLandingPadSizeRequestEntity(systemName, stationName, requestingModule)));
    }
}