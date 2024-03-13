package io.edpn.backend.exploration.adapter.persistence.entity.mapper;

import io.edpn.backend.exploration.adapter.persistence.entity.MybatisStationMaxLandingPadSizeRequestEntity;
import io.edpn.backend.exploration.application.domain.StationMaxLandingPadSizeRequest;
import io.edpn.backend.util.Module;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;

class MybatisStationMaxLandingPadSizeRequestEntityMapperTest {

    private MybatisStationMaxLandingPadSizeRequestEntityMapper underTest;

    @BeforeEach
    public void setUp() {
        underTest = new MybatisStationMaxLandingPadSizeRequestEntityMapper();
    }

    @Test
    public void testMap_givenDto_shouldReturnDomainObject() {

        MybatisStationMaxLandingPadSizeRequestEntity dto = new MybatisStationMaxLandingPadSizeRequestEntity("systemName", "stationName", mock(Module.class));


        StationMaxLandingPadSizeRequest result = underTest.map(dto);


        assertThat(result.systemName(), equalTo(dto.getSystemName()));
        assertThat(result.stationName(), equalTo(dto.getStationName()));
        assertThat(result.requestingModule(), equalTo(dto.getRequestingModule()));
    }

    @Test
    public void testMap_givenDomainObject_shouldReturnEntity() {

        StationMaxLandingPadSizeRequest domainObject = new StationMaxLandingPadSizeRequest("systemName", "stationName", mock(Module.class));


        MybatisStationMaxLandingPadSizeRequestEntity result = underTest.map(domainObject);


        assertThat(result.getSystemName(), equalTo(domainObject.systemName()));
        assertThat(result.getStationName(), equalTo(domainObject.stationName()));
        assertThat(result.getRequestingModule(), equalTo(domainObject.requestingModule()));
    }
}