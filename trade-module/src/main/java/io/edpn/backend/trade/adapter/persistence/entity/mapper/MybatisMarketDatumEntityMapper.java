package io.edpn.backend.trade.adapter.persistence.entity.mapper;

import io.edpn.backend.trade.adapter.persistence.entity.MybatisMarketDatumEntity;
import io.edpn.backend.trade.application.domain.MarketDatum;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MybatisMarketDatumEntityMapper {

    private final MybatisCommodityEntityMapper mybatisCommodityEntityMapper;

    public MarketDatum map(MybatisMarketDatumEntity mybatisMarketDatumEntity) {
        return new MarketDatum(
                //TODO: Remap to validatedCommodity?
                mybatisCommodityEntityMapper.map(mybatisMarketDatumEntity.getCommodity()),
                mybatisMarketDatumEntity.getTimestamp(),
                mybatisMarketDatumEntity.getMeanPrice(),
                mybatisMarketDatumEntity.getBuyPrice(),
                mybatisMarketDatumEntity.getStock(),
                mybatisMarketDatumEntity.getStockBracket(),
                mybatisMarketDatumEntity.getSellPrice(),
                mybatisMarketDatumEntity.getDemand(),
                mybatisMarketDatumEntity.getDemandBracket(),
                mybatisMarketDatumEntity.getStatusFlags(),
                mybatisMarketDatumEntity.isProhibited()
        );
    }

    public MybatisMarketDatumEntity map(MarketDatum marketDatum) {
        return MybatisMarketDatumEntity.builder()
                .commodity(mybatisCommodityEntityMapper.map(marketDatum.commodity()))
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
