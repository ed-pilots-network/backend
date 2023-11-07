package io.edpn.backend.exploration.adapter.persistence.entity.mapper;

import io.edpn.backend.exploration.adapter.persistence.entity.MybatisSystemCoordinateRequestEntity;
import io.edpn.backend.exploration.application.domain.SystemCoordinateRequest;
import io.edpn.backend.exploration.application.dto.persistence.entity.SystemCoordinateRequestEntity;
import io.edpn.backend.exploration.application.dto.persistence.entity.mapper.SystemCoordinateRequestEntityMapper;
import io.edpn.backend.util.Module;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;

class MybatisMybatisSystemCoordinateRequestEntityMapperTest {

    private SystemCoordinateRequestEntityMapper<MybatisSystemCoordinateRequestEntity> underTest;

    @BeforeEach
    public void setUp() {
        underTest = new MybatisSystemCoordinateRequestEntityMapper();
    }

    @Test
    public void testMap_givenDto_shouldReturnDomainObject() {

        SystemCoordinateRequestEntity dto = new MybatisSystemCoordinateRequestEntity("systemName", mock(Module.class));


        SystemCoordinateRequest result = underTest.map(dto);


        assertThat(result.systemName(), equalTo(dto.getSystemName()));
        assertThat(result.requestingModule(), equalTo(dto.getRequestingModule()));
    }

    @Test
    public void testMap_givenDomainObject_shouldReturnEntity() {

        SystemCoordinateRequest domainObject = new SystemCoordinateRequest("systemName", mock(Module.class));


        SystemCoordinateRequestEntity result = underTest.map(domainObject);


        assertThat(result.getSystemName(), equalTo(domainObject.systemName()));
        assertThat(result.getRequestingModule(), equalTo(domainObject.requestingModule()));
    }
}