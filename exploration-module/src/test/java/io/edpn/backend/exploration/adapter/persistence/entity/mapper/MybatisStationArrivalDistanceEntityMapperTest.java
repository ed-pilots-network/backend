package io.edpn.backend.exploration.adapter.persistence.entity.mapper;

import io.edpn.backend.exploration.adapter.persistence.entity.MybatisStationArrivalDistanceRequestEntity;
import io.edpn.backend.exploration.application.domain.StationArrivalDistanceRequest;
import io.edpn.backend.util.Module;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;

class MybatisStationArrivalDistanceEntityMapperTest {

    private MybatisStationArrivalDistanceRequestEntityMapper underTest;

    @BeforeEach
    public void setUp() {
        underTest = new MybatisStationArrivalDistanceRequestEntityMapper();
    }

    @Test
    public void testMap_givenDto_shouldReturnDomainObject() {
        MybatisStationArrivalDistanceRequestEntity dto = new MybatisStationArrivalDistanceRequestEntity("systemName", "stationName", mock(Module.class));

        StationArrivalDistanceRequest result = underTest.map(dto);

        assertThat(result.systemName(), equalTo(dto.getSystemName()));
        assertThat(result.stationName(), equalTo(dto.getStationName()));
        assertThat(result.requestingModule(), equalTo(dto.getRequestingModule()));
    }

    @Test
    public void testMap_givenDomainObject_shouldReturnEntity() {
        StationArrivalDistanceRequest domainObject = new StationArrivalDistanceRequest("systemName", "stationName", mock(Module.class));

        MybatisStationArrivalDistanceRequestEntity result = underTest.map(domainObject);

        assertThat(result.getSystemName(), equalTo(domainObject.systemName()));
        assertThat(result.getStationName(), equalTo(domainObject.stationName()));
        assertThat(result.getRequestingModule(), equalTo(domainObject.requestingModule()));
    }
}
