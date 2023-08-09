package io.edpn.backend.trade.adapter.persistence.entity.mapper;

import io.edpn.backend.trade.adapter.persistence.entity.MybatisFindCommodityFilter;
import io.edpn.backend.trade.application.dto.FindCommodityDTO;
import io.edpn.backend.trade.application.dto.mapper.FindCommodityEntityMapper;

public class MybatisFindCommodityMapper implements FindCommodityEntityMapper<MybatisFindCommodityFilter> {
    @Override
    public MybatisFindCommodityFilter map(FindCommodityDTO findCommodityDTO) {
        return MybatisFindCommodityFilter.builder()
                .type(findCommodityDTO.getType())
                .isRare(findCommodityDTO.getIsRare())
                .build();
    }
}
