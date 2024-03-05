package io.edpn.backend.trade.adapter.persistence.entity.mapper;

import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.StationDataRequest;
import io.edpn.backend.trade.adapter.persistence.entity.StationDataRequestEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(MockitoExtension.class)
class StationDataRequestEntityMapperTest {


    private StationDataRequestEntityMapper underTest;

    @BeforeEach
    public void setUp() {
        underTest = new StationDataRequestEntityMapper();
    }

    @Test
    public void testMap_givenEntity_shouldReturnDomainObject() {
        // Setup the Message Entity with test data
        StationDataRequestEntity entity = StationDataRequestEntity.builder()
                .stationName("station Name")
                .systemName("systemName")
                .build();

        // Map the entity to a Message object
        StationDataRequest domainObject = underTest.map(entity);

        // Verify that the result matches the expected values
        assertThat(domainObject.stationName(), is("station Name"));
        assertThat(domainObject.systemName(), is("systemName"));
    }
}