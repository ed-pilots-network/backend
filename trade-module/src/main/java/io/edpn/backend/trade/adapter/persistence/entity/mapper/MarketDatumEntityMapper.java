package io.edpn.backend.trade.adapter.persistence.entity.mapper;

import io.edpn.backend.trade.adapter.persistence.entity.MarketDatumEntity;
import io.edpn.backend.trade.application.domain.MarketDatum;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MarketDatumEntityMapper {

    private final CommodityEntityMapper commodityEntityMapper;

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

    public MarketDatumEntity map(MarketDatum marketDatum) {
        return MarketDatumEntity.builder()
                .commodity(commodityEntityMapper.map(marketDatum.commodity()))
                .timestamp(marketDatum.timestamp())
                .meanPrice(marketDatum.meanPrice())
                .buyPrice(marketDatum.buyPrice())
                .stock(marketDatum.stock())
                .stockBracket(marketDatum.stockBracket())
                .sellPrice(marketDatum.sellPrice())
                .demand(marketDatum.demand())
                .demandBracket(marketDatum.demandBracket())
                .statusFlags(marketDatum.statusFlags())
                .prohibited(marketDatum.prohibited())
                .build();
    }
}
