package io.edpn.backend.trade.adapter.web.dto.filter.mapper;

import io.edpn.backend.trade.application.domain.filter.PageFilter;
import io.edpn.backend.trade.application.dto.web.filter.PageFilterDto;
import io.edpn.backend.trade.application.dto.web.filter.mapper.PageFilterDtoMapper;

public class RestPageFilterDtoMapper implements PageFilterDtoMapper {
    @Override
    public PageFilter map(PageFilterDto pageFilterDto) {
        PageFilter.PageFilterBuilder bulder = getDefaultFilterBuilder();
        if (pageFilterDto.size() > 0) {
            bulder.size(pageFilterDto.size());
        }
        if (pageFilterDto.page() >= 0) {
            bulder.page(pageFilterDto.page());
        }
        return bulder.build();
    }

    @Override
    public PageFilter getDefaultFilter() {
        return getDefaultFilterBuilder()
                .build();
    }

    private PageFilter.PageFilterBuilder getDefaultFilterBuilder() {
        return PageFilter.builder()
                .size(20) //TODO get from config
                .page(0);
    }
}