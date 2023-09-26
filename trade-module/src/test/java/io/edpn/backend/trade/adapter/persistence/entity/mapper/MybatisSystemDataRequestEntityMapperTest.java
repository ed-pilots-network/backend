package io.edpn.backend.trade.adapter.persistence.entity.mapper;

import io.edpn.backend.messageprocessorlib.application.dto.eddn.data.SystemDataRequest;
import io.edpn.backend.trade.adapter.persistence.entity.MybatisSystemDataRequestEntity;
import io.edpn.backend.trade.application.dto.persistence.entity.mapper.SystemDataRequestEntityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(MockitoExtension.class)
class MybatisSystemDataRequestEntityMapperTest {


    private SystemDataRequestEntityMapper underTest;

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