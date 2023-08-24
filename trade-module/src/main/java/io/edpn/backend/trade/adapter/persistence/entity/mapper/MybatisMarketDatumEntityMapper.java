package io.edpn.backend.trade.adapter.persistence.entity.mapper;

import io.edpn.backend.trade.adapter.persistence.entity.MybatisCommodityEntity;
import io.edpn.backend.trade.adapter.persistence.entity.MybatisMarketDatumEntity;
import io.edpn.backend.trade.application.domain.MarketDatum;
import io.edpn.backend.trade.application.dto.persistence.entity.MarketDatumEntity;
import io.edpn.backend.trade.application.dto.persistence.entity.mapper.CommodityEntityMapper;
import io.edpn.backend.trade.application.dto.persistence.entity.mapper.MarketDatumEntityMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MybatisMarketDatumEntityMapper implements MarketDatumEntityMapper<MybatisMarketDatumEntity> {
    
    private final CommodityEntityMapper<MybatisCommodityEntity> commodityEntityMapper;
    
    @Override
    public MarketDatum map(MarketDatumEntity marketDatumEntity) {
        return new MarketDatum(
                //TODO: Remap to validatedCommodity?
                commodityEntityMapper.map(marketDatumEntity.getCommodity()),
                marketDatumEntity.getTimestamp(),
                marketDatumEntity.getMeanPrice(),
                marketDatumEntity.getBuyPrice(),
                marketDatumEntity.getStock(),
                marketDatumEntity.getStockBracket(),
                marketDatumEntity.getSellPrice(),
                marketDatumEntity.getDemand(),
                marketDatumEntity.getDemandBracket(),
                marketDatumEntity.getStatusFlags(),
                marketDatumEntity.isProhibited()
        );
    }
    
    @Override
    public MybatisMarketDatumEntity map(MarketDatum marketDatum) {
        return MybatisMarketDatumEntity.builder()
                .commodity(commodityEntityMapper.map(marketDatum.getCommodity()))
                .timestamp(marketDatum.getTimestamp())
                .meanPrice(marketDatum.getMeanPrice())
                .buyPrice(marketDatum.getBuyPrice())
                .stock(marketDatum.getStock())
                .stockBracket(marketDatum.getStockBracket())
                .sellPrice(marketDatum.getSellPrice())
                .demand(marketDatum.getDemand())
                .demandBracket(marketDatum.getDemandBracket())
                .statusFlags(marketDatum.getStatusFlags())
                .prohibited(marketDatum.isProhibited())
                .build();
    }
}
