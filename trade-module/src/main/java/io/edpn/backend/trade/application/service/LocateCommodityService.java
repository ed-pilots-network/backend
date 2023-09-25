package io.edpn.backend.trade.application.service;

import io.edpn.backend.trade.application.domain.LocateCommodity;
import io.edpn.backend.trade.application.domain.PagedResult;
import io.edpn.backend.trade.application.domain.filter.LocateCommodityFilter;
import io.edpn.backend.trade.application.dto.web.filter.LocateCommodityFilterDto;
import io.edpn.backend.trade.application.dto.web.filter.mapper.LocateCommodityFilterDtoMapper;
import io.edpn.backend.trade.application.dto.web.object.LocateCommodityDto;
import io.edpn.backend.trade.application.dto.web.object.PageInfoDto;
import io.edpn.backend.trade.application.dto.web.object.PagedResultDto;
import io.edpn.backend.trade.application.dto.web.object.mapper.LocateCommodityDtoMapper;
import io.edpn.backend.trade.application.dto.web.object.mapper.PageInfoDtoMapper;
import io.edpn.backend.trade.application.dto.web.object.mapper.PagedResultDtoMapper;
import io.edpn.backend.trade.application.port.incomming.locatecommodity.LocateCommodityUseCase;
import io.edpn.backend.trade.application.port.outgoing.locatecommodity.LocateCommodityByFilterPort;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.BiFunction;

@RequiredArgsConstructor
public class LocateCommodityService implements LocateCommodityUseCase {

    private final LocateCommodityByFilterPort locateCommodityByFilterPort;
    private final LocateCommodityFilterDtoMapper locateCommodityFilterDtoMapper;
    private final LocateCommodityDtoMapper locateCommodityDtoMapper;
    private final PageInfoDtoMapper pageInfoDtoMapper;

    @Override
    public <T extends LocateCommodityDto, U extends PagedResultDto<T, R>, R extends PageInfoDto> PagedResultDto<T, R> locateCommodityOrderByDistance(LocateCommodityFilterDto locateCommodityFilterDto, BiFunction<List<T>, R, U> pagedResultConstructor) {
        LocateCommodityFilter filter = locateCommodityFilterDtoMapper.map(locateCommodityFilterDto);
        PagedResult<LocateCommodity> locateCommodityPagedResult = locateCommodityByFilterPort.locateCommodityByFilter(filter);

        return PagedResultDtoMapper.map(
                locateCommodityPagedResult,
                locateCommodityDtoMapper::map,
                pageInfoDtoMapper::map,
                pagedResultConstructor
        );
    }
}
