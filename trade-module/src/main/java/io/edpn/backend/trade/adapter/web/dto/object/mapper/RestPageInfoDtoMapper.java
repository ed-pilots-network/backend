package io.edpn.backend.trade.adapter.web.dto.object.mapper;

import io.edpn.backend.trade.adapter.web.dto.object.RestPageInfoDto;
import io.edpn.backend.trade.application.domain.PageInfo;
import io.edpn.backend.trade.application.dto.web.object.mapper.PageInfoDtoMapper;

public class RestPageInfoDtoMapper implements PageInfoDtoMapper {
    @Override
    public RestPageInfoDto map(PageInfo pageInfo) {
        return new RestPageInfoDto(
                pageInfo.getPageSize(),
                pageInfo.getCurrentPage(),
                pageInfo.getTotalItems()
        );
    }
}
