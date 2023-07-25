package io.edpn.backend.exploration.infrastructure.persistence.mappers.entity;

import io.edpn.backend.exploration.domain.model.SystemCoordinateDataRequest;
import io.edpn.backend.exploration.infrastructure.persistence.entity.SystemCoordinateDataRequestEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SystemCoordinateDataRequestMapperTest {

    private SystemCoordinateDataRequestMapper underTest;

    @BeforeEach
    void setUp() {
        underTest = new SystemCoordinateDataRequestMapper();
    }

    @Test
    void shouldMapEntityToModel() {
        String systemName = "Test System";
        String requestingModule = "Test Module";

        SystemCoordinateDataRequestEntity entity = SystemCoordinateDataRequestEntity.builder()
                .systemName(systemName)
                .requestingModule(requestingModule)
                .build();

        SystemCoordinateDataRequest result = underTest.map(entity);

        assertThat(result.getSystemName(), is(systemName));
        assertThat(result.getRequestingModule(), is(requestingModule));
    }

    @Test
    void shouldMapModelToEntity() {
        String systemName = "Test System";
        String requestingModule = "Test Module";

        SystemCoordinateDataRequest model = SystemCoordinateDataRequest.builder()
                .systemName(systemName)
                .requestingModule(requestingModule)
                .build();

        SystemCoordinateDataRequestEntity result = underTest.map(model);

        assertThat(result.getSystemName(), is(systemName));
        assertThat(result.getRequestingModule(), is(requestingModule));
    }
}
