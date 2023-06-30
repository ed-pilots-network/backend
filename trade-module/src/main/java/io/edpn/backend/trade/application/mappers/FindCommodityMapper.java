package io.edpn.backend.trade.application.mappers;

import io.edpn.backend.trade.application.dto.FindCommodityResponse;
import io.edpn.backend.trade.domain.model.MarketDatum;
import lombok.RequiredArgsConstructor;

//TODO: expand to match actual response data
@RequiredArgsConstructor
public class FindCommodityMapper {
    
    public FindCommodityResponse map(MarketDatum marketDatum){
        return FindCommodityResponse.builder()
                .PricesUpdate(marketDatum.getTimestamp())
                .build();
    }
    
}
