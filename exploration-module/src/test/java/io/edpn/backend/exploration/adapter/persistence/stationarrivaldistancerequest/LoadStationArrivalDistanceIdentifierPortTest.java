package io.edpn.backend.exploration.adapter.persistence.stationarrivaldistancerequest;

import io.edpn.backend.exploration.adapter.persistence.MybatisStationArrivalDistanceRequestRepository;
import io.edpn.backend.exploration.adapter.persistence.StationArrivalDistanceRequestRepository;
import io.edpn.backend.exploration.adapter.persistence.entity.MybatisStationArrivalDistanceRequestEntity;
import io.edpn.backend.exploration.adapter.persistence.entity.mapper.MybatisStationArrivalDistanceRequestEntityMapper;
import io.edpn.backend.exploration.application.domain.StationArrivalDistanceRequest;
import io.edpn.backend.exploration.application.port.outgoing.stationarrivaldistancerequest.LoadStationArrivalDistanceRequestByIdentifierPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoadStationArrivalDistanceIdentifierPortTest {

    @Mock
    private MybatisStationArrivalDistanceRequestRepository mybatisStationArrivalDistanceRequestRepository;

    @Mock
    private MybatisStationArrivalDistanceRequestEntityMapper mybatisStationArrivalDistanceRequestEntityMapper;

    private LoadStationArrivalDistanceRequestByIdentifierPort underTest;

    @BeforeEach
    void setUp() {
        underTest = new StationArrivalDistanceRequestRepository(mybatisStationArrivalDistanceRequestRepository, mybatisStationArrivalDistanceRequestEntityMapper);
    }

    @Test
    void load_withSystemName_shouldFindBySystemNameAndMap() {

        String systemName = "system";
        String stationName = "station";
        MybatisStationArrivalDistanceRequestEntity mybatisStationArrivalDistanceRequestEntity = mock(MybatisStationArrivalDistanceRequestEntity.class);
        List<MybatisStationArrivalDistanceRequestEntity> entities = List.of(mybatisStationArrivalDistanceRequestEntity);
        StationArrivalDistanceRequest mapped = mock(StationArrivalDistanceRequest.class);
        when(mybatisStationArrivalDistanceRequestRepository.findByIdentifier(systemName, stationName)).thenReturn(entities);
        when(mybatisStationArrivalDistanceRequestEntityMapper.map(mybatisStationArrivalDistanceRequestEntity)).thenReturn(mapped);


        List<StationArrivalDistanceRequest> result = underTest.loadByIdentifier(systemName, stationName);


        assertThat(result, contains(mapped));
        verify(mybatisStationArrivalDistanceRequestRepository).findByIdentifier(systemName, stationName);
    }

}
