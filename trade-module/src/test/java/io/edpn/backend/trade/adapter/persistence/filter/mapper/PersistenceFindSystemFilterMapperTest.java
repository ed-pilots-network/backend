package io.edpn.backend.trade.adapter.persistence.filter.mapper;

import io.edpn.backend.trade.adapter.persistence.filter.FindSystemFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(MockitoExtension.class)
class PersistenceFindSystemFilterMapperTest {

    private FindSystemFilterMapper underTest;

    @BeforeEach
    public void setUp() {
        underTest = new FindSystemFilterMapper();
    }

    @Test
    public void testMap_givenDomainObject_shouldReturnEntity() {

        io.edpn.backend.trade.application.domain.filter.FindSystemFilter domainObject = io.edpn.backend.trade.application.domain.filter.FindSystemFilter.builder()
                .name("Name")
                .hasEliteId(true)
                .hasCoordinates(false)
                .build();

        FindSystemFilter entity = underTest.map(domainObject);

        assertThat(entity.getName(), is("Name"));
        assertThat(entity.getHasCoordinates(), is(false));
        assertThat(entity.getHasEliteId(), is(true));
    }
}