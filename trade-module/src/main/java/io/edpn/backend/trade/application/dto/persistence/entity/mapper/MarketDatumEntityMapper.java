package io.edpn.backend.trade.application.dto.persistence.entity.mapper;

import io.edpn.backend.trade.application.domain.MarketDatum;
import io.edpn.backend.trade.application.dto.persistence.entity.MarketDatumEntity;

public interface MarketDatumEntityMapper<T extends MarketDatumEntity>{
    
    MarketDatum map(MarketDatumEntity marketDatumEntity);
    
    T map(MarketDatum marketDatum);
}
