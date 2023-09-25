package io.edpn.backend.trade.adapter.persistence.filter.mapper;

import io.edpn.backend.trade.application.domain.filter.PageFilter;
import io.edpn.backend.trade.application.dto.persistence.filter.PersistencePageFilter;
import io.edpn.backend.trade.application.dto.persistence.filter.mapper.PersistencePageFilterMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(MockitoExtension.class)
class MybatisPageFilterMapperTest {

    private PersistencePageFilterMapper underTest;

    @BeforeEach
    public void setUp() {
        underTest = new MybatisPersistencePageFilterMapper();
    }

    @Test
    public void testMap_givenDomainObject_shouldReturnEntity() {
        PageFilter pageFilter = PageFilter.builder()
                .page(1)
                .size(20)
                .build();

        PersistencePageFilter entity = underTest.map(pageFilter);

        assertThat(entity.getPage(), is(1));
        assertThat(entity.getSize(), is(20));
    }
}