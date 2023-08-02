package io.edpn.backend.exploration.adapter.persistence.entity.mapper;

import io.edpn.backend.exploration.adapter.persistence.entity.SystemCoordinateRequestEntity;
import io.edpn.backend.exploration.application.domain.SystemCoordinateRequest;
import io.edpn.backend.exploration.application.dto.SystemCoordinateRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class SystemCoordinateRequestEntityMapperTest {

    private io.edpn.backend.exploration.application.dto.mapper.SystemCoordinateRequestEntityMapper underTest;

    @BeforeEach
    public void setUp() {
        underTest = new SystemCoordinateRequestEntityMapper();
    }

    @Test
    public void testMap_givenDto_shouldReturnDomainObject() {
        // Given
        SystemCoordinateRequestDto dto = new SystemCoordinateRequestEntity("systemName", "requestingModule");

        // When
        SystemCoordinateRequest result = underTest.map(dto);

        // Then
        assertThat(result.systemName(), equalTo(dto.systemName()));
        assertThat(result.requestingModule(), equalTo(dto.requestingModule()));
    }

    @Test
    public void testMap_givenDomainObject_shouldReturnEntity() {
        // Given
        SystemCoordinateRequest domainObject = new SystemCoordinateRequest("systemName", "requestingModule");

        // When
        SystemCoordinateRequestDto result = underTest.map(domainObject);

        // Then
        assertThat(result.systemName(), equalTo(domainObject.systemName()));
        assertThat(result.requestingModule(), equalTo(domainObject.requestingModule()));
    }
}