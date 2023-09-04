package io.edpn.backend.trade.application.service;

import io.edpn.backend.trade.adapter.web.dto.object.RestLocateCommodityDto;
import io.edpn.backend.trade.adapter.web.dto.object.RestPageInfoDto;
import io.edpn.backend.trade.adapter.web.dto.object.RestPagedResultDto;
import io.edpn.backend.trade.application.domain.LocateCommodity;
import io.edpn.backend.trade.application.domain.PageInfo;
import io.edpn.backend.trade.application.domain.PagedResult;
import io.edpn.backend.trade.application.domain.filter.LocateCommodityFilter;
import io.edpn.backend.trade.application.dto.web.filter.LocateCommodityFilterDto;
import io.edpn.backend.trade.application.dto.web.filter.mapper.LocateCommodityFilterDtoMapper;
import io.edpn.backend.trade.application.dto.web.object.LocateCommodityDto;
import io.edpn.backend.trade.application.dto.web.object.PagedResultDto;
import io.edpn.backend.trade.application.dto.web.object.mapper.LocateCommodityDtoMapper;
import io.edpn.backend.trade.application.dto.web.object.mapper.PageInfoDtoMapper;
import io.edpn.backend.trade.application.port.incomming.locatecommodity.LocateCommodityUseCase;
import io.edpn.backend.trade.application.port.outgoing.locatecommodity.LocateCommodityByFilterPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.function.BiFunction;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LocateCommodityUseCaseTest {

    @Mock
    private LocateCommodityByFilterPort locateCommodityByFilterPort;

    @Mock
    private LocateCommodityFilterDtoMapper locateCommodityFilterDtoMapper;

    @Mock
    private LocateCommodityDtoMapper locateCommodityDtoMapper;

    @Mock
    private PageInfoDtoMapper pageInfoDtoMapper;

    private LocateCommodityUseCase underTest;

    @BeforeEach
    public void setUp() {
        underTest = new LocateCommodityService(locateCommodityByFilterPort, locateCommodityFilterDtoMapper, locateCommodityDtoMapper, pageInfoDtoMapper);
    }

    @Test
    void shouldLocateCommoditiesNearby() {
        LocateCommodityFilterDto locateCommodityFilterDto = mock(LocateCommodityFilterDto.class);
        LocateCommodityFilter locateCommodityFilter = mock(LocateCommodityFilter.class);
        LocateCommodity locateCommodity = mock(LocateCommodity.class);
        LocateCommodityDto locateCommodityDto = mock(LocateCommodityDto.class);
        PageInfo pageInfo = mock(PageInfo.class);
        PagedResult<LocateCommodity> pagedResult = new PagedResult<>(List.of(locateCommodity), pageInfo);
        RestPageInfoDto pageInfoDto = mock(RestPageInfoDto.class);

        when(locateCommodityFilterDtoMapper.map(locateCommodityFilterDto)).thenReturn(locateCommodityFilter);
        when(locateCommodityByFilterPort.locateCommodityByFilter(locateCommodityFilter)).thenReturn(pagedResult);
        when(locateCommodityDtoMapper.map(locateCommodity)).thenReturn(locateCommodityDto);
        when(pageInfoDtoMapper.map(pageInfo)).thenReturn(pageInfoDto);
        BiFunction<List<RestLocateCommodityDto>, RestPageInfoDto, RestPagedResultDto<RestLocateCommodityDto>> constructor = RestPagedResultDto::new;

        PagedResultDto<RestLocateCommodityDto> responses = underTest.locateCommodityOrderByDistance(locateCommodityFilterDto, constructor);

        ArgumentCaptor<LocateCommodityFilter> argumentCaptor = ArgumentCaptor.forClass(LocateCommodityFilter.class);
        verify(locateCommodityByFilterPort).locateCommodityByFilter(argumentCaptor.capture());

        LocateCommodityFilter capturedLocateCommodity = argumentCaptor.getValue();

        assertThat(capturedLocateCommodity, equalTo(locateCommodityFilter));
        assertThat(responses, notNullValue());
        assertThat(responses.pageInfo(), is(pageInfoDto));
        assertThat(responses.result(), equalTo(List.of(locateCommodityDto)));
    }

}
