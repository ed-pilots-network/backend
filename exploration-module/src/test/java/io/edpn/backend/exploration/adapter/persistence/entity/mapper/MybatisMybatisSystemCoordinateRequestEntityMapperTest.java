package io.edpn.backend.exploration.adapter.persistence.entity.mapper;

import io.edpn.backend.exploration.adapter.persistence.entity.MybatisSystemCoordinateRequestEntity;
import io.edpn.backend.exploration.application.domain.SystemCoordinateRequest;
import io.edpn.backend.exploration.application.dto.SystemCoordinateRequestEntity;
import io.edpn.backend.exploration.application.dto.mapper.SystemCoordinateRequestEntityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class MybatisMybatisSystemCoordinateRequestEntityMapperTest {

    private SystemCoordinateRequestEntityMapper<MybatisSystemCoordinateRequestEntity> underTest;

    @BeforeEach
    public void setUp() {
        underTest = new MybatisSystemCoordinateRequestEntityMapper();
    }

    @Test
    public void testMap_givenDto_shouldReturnDomainObject() {

        SystemCoordinateRequestEntity dto = new MybatisSystemCoordinateRequestEntity("systemName", "requestingModule");


        SystemCoordinateRequest result = underTest.map(dto);


        assertThat(result.systemName(), equalTo(dto.getSystemName()));
        assertThat(result.requestingModule(), equalTo(dto.getRequestingModule()));
    }

    @Test
    public void testMap_givenDomainObject_shouldReturnEntity() {

        SystemCoordinateRequest domainObject = new SystemCoordinateRequest("systemName", "requestingModule");


        SystemCoordinateRequestEntity result = underTest.map(domainObject);


        assertThat(result.getSystemName(), equalTo(domainObject.systemName()));
        assertThat(result.getRequestingModule(), equalTo(domainObject.requestingModule()));
    }
}