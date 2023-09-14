package io.edpn.backend.trade.adapter.web.dto.filter.mapper;

import io.edpn.backend.trade.application.domain.filter.PageFilter;
import io.edpn.backend.trade.application.dto.web.filter.PageFilterDto;
import io.edpn.backend.trade.application.dto.web.filter.mapper.PageFilterDtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(MockitoExtension.class)
class RestPageFilterDtoMapperTest {

    private PageFilterDtoMapper underTest;

    public static Stream<Arguments> testMapSource() {
        return Stream.of(
                Arguments.of(4, 1, 4, 1),
                Arguments.of(-20, 0, 20, 0),
                Arguments.of(17, -1, 17, 0),
                Arguments.of(0, 8, 20, 8),
                Arguments.of(null, 1, 20, 1),
                Arguments.of(null, null, 20, 0),
                Arguments.of(16, null, 16, 0)
        );
    }

    @BeforeEach
    public void setUp() {
        underTest = new RestPageFilterDtoMapper();
    }

    @ParameterizedTest
    @MethodSource("testMapSource")
    public void testMap(Integer size, Integer page, Integer expectedSize, Integer expectedPage) {
        PageFilterDto pageFilterDto = buildPageFilterDto(size, page);

        PageFilter domainObject = underTest.map(pageFilterDto);

        assertThat(domainObject.getSize(), is(expectedSize));
        assertThat(domainObject.getPage(), is(expectedPage));
    }

    @Test
    public void testMap_givenDto_shouldReturnDomainObject() {
        PageFilterDto pageFilterDto = buildPageFilterDto(20, 1);

        PageFilter domainObject = underTest.map(pageFilterDto);

        assertThat(domainObject.getPage(), is(1));
        assertThat(domainObject.getSize(), is(20));
    }

    private PageFilterDto buildPageFilterDto(Integer size, Integer page) {
        return new PageFilterDto() {
            @Override
            public Integer size() {
                return size;
            }

            @Override
            public Integer page() {
                return page;
            }
        };
    }
}