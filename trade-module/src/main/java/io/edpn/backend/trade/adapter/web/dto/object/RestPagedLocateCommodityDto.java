package io.edpn.backend.trade.adapter.web.dto.object;


import java.io.Serializable;
import java.util.List;

public class RestPagedLocateCommodityDto extends RestPagedResultDto<RestLocateCommodityDto> implements Serializable {

    public RestPagedLocateCommodityDto(List<RestLocateCommodityDto> result, RestPageInfoDto pageInfo) {
        super(result, pageInfo);
    }
}
