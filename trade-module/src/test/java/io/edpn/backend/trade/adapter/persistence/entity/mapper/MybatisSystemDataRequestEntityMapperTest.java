package io.edpn.backend.trade.adapter.persistence.entity.mapper;

import io.edpn.backend.trade.application.domain.intermodulecommunication.SystemDataRequest;
import io.edpn.backend.trade.adapter.persistence.entity.MybatisSystemDataRequestEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(MockitoExtension.class)
class MybatisSystemDataRequestEntityMapperTest {

    private MybatisSystemDataRequestEntityMapper underTest;

    @BeforeEach
    public void setUp() {
        underTest = new MybatisSystemDataRequestEntityMapper();
    }

    @Test
    public void testMap_givenEntity_shouldReturnDomainObject() {
        // Setup the Message Entity with test data
        MybatisSystemDataRequestEntity entity = MybatisSystemDataRequestEntity.builder()
                .systemName("systemName")
                .build();

        // Map the entity to a Message object
        SystemDataRequest domainObject = underTest.map(entity);

        // Verify that the result matches the expected values
        assertThat(domainObject.systemName(), is("systemName"));
    }
}