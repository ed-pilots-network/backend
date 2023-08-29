package io.edpn.backend.trade.adapter.web.dto.filter.mapper;

import io.edpn.backend.trade.adapter.web.dto.filter.RestPageFilterDto;
import io.edpn.backend.trade.application.domain.filter.PageFilter;
import io.edpn.backend.trade.application.dto.web.filter.mapper.PageFilterDtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(MockitoExtension.class)
class RestPageFilterDtoMapperTest {

    private PageFilterDtoMapper underTest;

    @BeforeEach
    public void setUp() {
        underTest = new RestPageFilterDtoMapper();
    }

    @Test
    public void testMap_givenDto_shouldReturnDomainObject() {
        RestPageFilterDto pageFilterDto = new RestPageFilterDto(20, 1);

        PageFilter domainObject = underTest.map(pageFilterDto);

        assertThat(domainObject.getPage(), is(1));
        assertThat(domainObject.getSize(), is(20));
    }

    @Test
    public void testMap_givenDto_shouldReturnDomainObject_negativePageShouldBeZero() {
        RestPageFilterDto pageFilterDto = new RestPageFilterDto(20, -4);

        PageFilter domainObject = underTest.map(pageFilterDto);

        assertThat(domainObject.getPage(), is(0));
        assertThat(domainObject.getSize(), is(20));
    }

    @Test
    public void testMap_givenDto_shouldReturnDomainObject_negativeSizeShouldBeDefault() {
        RestPageFilterDto pageFilterDto = new RestPageFilterDto(-20, 8);

        PageFilter domainObject = underTest.map(pageFilterDto);

        assertThat(domainObject.getPage(), is(8));
        assertThat(domainObject.getSize(), is(underTest.getDefaultFilter().getSize()));
    }
}