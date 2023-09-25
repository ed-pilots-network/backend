package io.edpn.backend.trade.adapter.persistence.entity.mapper;

import io.edpn.backend.trade.application.domain.PageInfo;
import io.edpn.backend.trade.application.dto.persistence.entity.PersistencePageInfo;
import io.edpn.backend.trade.application.dto.persistence.entity.mapper.PersistencePageInfoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MybatisPageInfoMapperTest {

    private PersistencePageInfoMapper underTest;

    @BeforeEach
    public void setUp() {
        underTest = new MybatisPageInfoMapper();
    }

    @Test
    public void testMap_givenEntity_shouldReturnDomainObject() {
        PersistencePageInfo entity = mock(PersistencePageInfo.class);
        when(entity.getCurrentPage()).thenReturn(1);
        when(entity.getPageSize()).thenReturn(20);
        when(entity.getTotalItems()).thenReturn(187456);

        PageInfo result = underTest.map(entity);

        assertThat(result.getCurrentPage(), equalTo(1));
        assertThat(result.getPageSize(), equalTo(20));
        assertThat(result.getTotalItems(), equalTo(187456));
    }

}