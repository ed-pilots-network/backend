package io.edpn.backend.trade.adapter.persistence.stationarrivaldistancerequest;

import io.edpn.backend.trade.adapter.persistence.StationArrivalDistanceRequestRepository;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisStationArrivalDistanceRequestRepository;
import io.edpn.backend.trade.application.port.outgoing.stationarrivaldistancerequest.DeleteStationArrivalDistanceRequestPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class DeleteStationArrivalDistanceRequestPortTest {

    @Mock
    private MybatisStationArrivalDistanceRequestRepository mybatisStationArrivalDistanceRequestRepository;

    private DeleteStationArrivalDistanceRequestPort underTest;

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
