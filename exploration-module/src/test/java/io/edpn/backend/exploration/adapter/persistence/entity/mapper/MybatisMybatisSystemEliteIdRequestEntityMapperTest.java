package io.edpn.backend.exploration.adapter.persistence.entity.mapper;

import io.edpn.backend.exploration.adapter.persistence.entity.MybatisSystemEliteIdRequestEntity;
import io.edpn.backend.exploration.application.domain.SystemEliteIdRequest;
import io.edpn.backend.exploration.application.dto.SystemEliteIdRequestEntity;
import io.edpn.backend.exploration.application.dto.mapper.SystemEliteIdRequestEntityMapper;
import io.edpn.backend.util.Module;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;

class MybatisMybatisSystemEliteIdRequestEntityMapperTest {

    private SystemEliteIdRequestEntityMapper<MybatisSystemEliteIdRequestEntity> underTest;

    @BeforeEach
    public void setUp() {
        underTest = new MybatisSystemEliteIdRequestEntityMapper();
    }

    @Test
    public void testMap_givenDto_shouldReturnDomainObject() {

        SystemEliteIdRequestEntity dto = new MybatisSystemEliteIdRequestEntity("systemName", mock(Module.class));


        SystemEliteIdRequest result = underTest.map(dto);


        assertThat(result.systemName(), equalTo(dto.getSystemName()));
        assertThat(result.requestingModule(), equalTo(dto.getRequestingModule()));
    }

    @Test
    public void testMap_givenDomainObject_shouldReturnEntity() {

        SystemEliteIdRequest domainObject = new SystemEliteIdRequest("systemName", mock(Module.class));


        SystemEliteIdRequestEntity result = underTest.map(domainObject);


        assertThat(result.getSystemName(), equalTo(domainObject.systemName()));
        assertThat(result.getRequestingModule(), equalTo(domainObject.requestingModule()));
    }
}