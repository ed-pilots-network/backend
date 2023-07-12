package io.edpn.backend.trade.infrastructure.persistence.mappers.entity;

import io.edpn.backend.trade.domain.model.Commodity;
import io.edpn.backend.trade.domain.model.MarketDatum;
import io.edpn.backend.trade.infrastructure.persistence.entity.CommodityEntity;
import io.edpn.backend.trade.infrastructure.persistence.entity.MarketDatumEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MarketDatumMapperTest {

    @Mock
    private CommodityMapper commodityMapper;

    private MarketDatumMapper underTest;

    @BeforeEach
    public void setup() {
        underTest = new MarketDatumMapper(commodityMapper);
    }

    @Test
    public void shouldMapMarketDatumEntityToMarketDatum() {
        UUID id = UUID.randomUUID();
        String name = "Commodity name";
        CommodityEntity commodityEntity = CommodityEntity.builder().id(id).name(name).build();
        Commodity commodity = Commodity.builder().id(id).name(name).build();

        LocalDateTime timestamp = LocalDateTime.now();
        long meanPrice = 100L;
        long buyPrice = 50L;
        long stock = 200L;
        long stockBracket = 10L;
        long sellPrice = 150L;
        long demand = 300L;
        long demandBracket = 20L;
        List<String> statusFlags = Arrays.asList("status1", "status2");
        boolean prohibited = false;

        MarketDatumEntity marketDatumEntity = MarketDatumEntity.builder()
                .commodity(commodityEntity)
                .timestamp(timestamp)
                .meanPrice(meanPrice)
                .buyPrice(buyPrice)
                .stock(stock)
                .stockBracket(stockBracket)
                .sellPrice(sellPrice)
                .demand(demand)
                .demandBracket(demandBracket)
                .statusFlags(statusFlags)
                .prohibited(prohibited)
                .build();

        when(commodityMapper.map(commodityEntity)).thenReturn(commodity);

        MarketDatum marketDatum = underTest.map(marketDatumEntity);

        assertThat(marketDatum.getCommodity().getId(), is(id));
        assertThat(marketDatum.getCommodity().getName(), is(name));
        assertThat(marketDatum.getTimestamp(), is(timestamp));
        assertThat(marketDatum.getMeanPrice(), is(meanPrice));
        assertThat(marketDatum.getBuyPrice(), is(buyPrice));
        assertThat(marketDatum.getStock(), is(stock));
        assertThat(marketDatum.getStockBracket(), is(stockBracket));
        assertThat(marketDatum.getSellPrice(), is(sellPrice));
        assertThat(marketDatum.getDemand(), is(demand));
        assertThat(marketDatum.getDemandBracket(), is(demandBracket));
        assertThat(marketDatum.getStatusFlags(), is(statusFlags));
        assertThat(marketDatum.isProhibited(), is(prohibited));
    }

    @Test
    public void shouldMapMarketDatumToMarketDatumEntity() {
        UUID id = UUID.randomUUID();
        String name = "Commodity name";
        CommodityEntity commodityEntity = CommodityEntity.builder().id(id).name(name).build();
        Commodity commodity = Commodity.builder().id(id).name(name).build();

        LocalDateTime timestamp = LocalDateTime.now();
        long meanPrice = 100L;
        long buyPrice = 50L;
        long stock = 200L;
        long stockBracket = 10L;
        long sellPrice = 150L;
        long demand = 300L;
        long demandBracket = 20L;
        List<String> statusFlags = Arrays.asList("status1", "status2");
        boolean prohibited = false;

        MarketDatum marketDatum = MarketDatum.builder()
                .commodity(commodity)
                .timestamp(timestamp)
                .meanPrice(meanPrice)
                .buyPrice(buyPrice)
                .stock(stock)
                .stockBracket(stockBracket)
                .sellPrice(sellPrice)
                .demand(demand)
                .demandBracket(demandBracket)
                .statusFlags(statusFlags)
                .prohibited(prohibited)
                .build();

        when(commodityMapper.map(commodity)).thenReturn(commodityEntity);

        MarketDatumEntity marketDatumEntity = underTest.mapToEntity(marketDatum);

        assertThat(marketDatumEntity.getCommodity().getId(), is(id));
        assertThat(marketDatumEntity.getCommodity().getName(), is(name));
        assertThat(marketDatumEntity.getTimestamp(), is(timestamp));
        assertThat(marketDatumEntity.getMeanPrice(), is(meanPrice));
        assertThat(marketDatumEntity.getBuyPrice(), is(buyPrice));
        assertThat(marketDatumEntity.getStock(), is(stock));
        assertThat(marketDatumEntity.getStockBracket(), is(stockBracket));
        assertThat(marketDatumEntity.getSellPrice(), is(sellPrice));
        assertThat(marketDatumEntity.getDemand(), is(demand));
        assertThat(marketDatumEntity.getDemandBracket(), is(demandBracket));
        assertThat(marketDatumEntity.getStatusFlags(), is(statusFlags));
        assertThat(marketDatumEntity.isProhibited(), is(prohibited));
    }
}
