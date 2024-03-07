package io.edpn.backend.trade.adapter.persistence.stationrequireodysseyrequest;

import io.edpn.backend.trade.adapter.persistence.StationRequireOdysseyRequestRepository;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.MybatisStationDataRequestEntityMapper;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisStationRequireOdysseyRequestRepository;
import io.edpn.backend.trade.application.port.outgoing.stationrequireodysseyrequest.CreateStationRequireOdysseyRequestPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CreateStationRequireOdysseyRequestPortTest {

    @Mock
    private MybatisStationRequireOdysseyRequestRepository mybatisStationRequireOdysseyRequestRepository;
    @Mock
    private MybatisStationDataRequestEntityMapper mybatisStationDataRequestEntityMapper;

    private CreateStationRequireOdysseyRequestPort underTest;

    @BeforeEach
    public void setup() {
        underTest = new StationRequireOdysseyRequestRepository(mybatisStationRequireOdysseyRequestRepository, mybatisStationDataRequestEntityMapper);
    }

    @Test
    public void testCreate() {
        String systemName = "someName";
        String stationName = "someName2";

        underTest.create(systemName, stationName);

        verify(mybatisStationRequireOdysseyRequestRepository).insert(systemName, stationName);
    }


}
