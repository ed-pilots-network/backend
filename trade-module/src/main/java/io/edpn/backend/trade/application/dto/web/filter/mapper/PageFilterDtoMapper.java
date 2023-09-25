package io.edpn.backend.trade.application.dto.web.filter.mapper;

import io.edpn.backend.trade.application.domain.filter.PageFilter;
import io.edpn.backend.trade.application.dto.web.filter.PageFilterDto;

public interface PageFilterDtoMapper {

    PageFilter map(PageFilterDto pageFilterDto);

    PageFilter getDefaultFilter();
}
