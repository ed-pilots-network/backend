package io.edpn.backend.trade.adapter.persistence.stationarrivaldistancerequest;

import io.edpn.backend.trade.adapter.persistence.StationArrivalDistanceRequestRepository;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisStationArrivalDistanceRequestRepository;
import io.edpn.backend.trade.application.port.outgoing.stationarrivaldistancerequest.DeleteStationRequireOdysseyRequestPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class DeleteStationRequireOdysseyRequestPortTest {

    @Mock
    private MybatisStationArrivalDistanceRequestRepository mybatisStationArrivalDistanceRequestRepository;

    private DeleteStationRequireOdysseyRequestPort underTest;

    @BeforeEach
    public void setup() {
        underTest = new StationArrivalDistanceRequestRepository(mybatisStationArrivalDistanceRequestRepository);
    }

    @Test
    public void testCreate() {
        String systemName = "someName";
        String stationName = "someName2";

        underTest.delete(systemName, stationName);

        verify(mybatisStationArrivalDistanceRequestRepository).delete(systemName, stationName);
    }


}
