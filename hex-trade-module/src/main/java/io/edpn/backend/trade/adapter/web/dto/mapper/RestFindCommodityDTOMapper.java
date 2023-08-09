package io.edpn.backend.trade.adapter.web.dto.mapper;

import io.edpn.backend.trade.adapter.web.dto.RestFindCommodityDTO;
import io.edpn.backend.trade.application.dto.FindCommodityDTO;
import io.edpn.backend.trade.application.dto.FindCommodityEntity;
import io.edpn.backend.trade.application.dto.mapper.FindCommodityDTOMapper;

public class RestFindCommodityDTOMapper implements FindCommodityDTOMapper {
    @Override
    public FindCommodityDTO map(FindCommodityEntity findCommodityEntity) {
        return new RestFindCommodityDTO(
                findCommodityEntity.getType(),
                findCommodityEntity.getIsRare());
    }
}
