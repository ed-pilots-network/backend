package io.edpn.backend.trade.adapter.persistence;

import io.edpn.backend.trade.adapter.persistence.entity.mapper.MybatisMarketDatumEntityMapper;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisMarketDatumRepository;
import io.edpn.backend.trade.application.domain.MarketDatum;
import io.edpn.backend.trade.application.port.outgoing.marketdatum.CreateMarketDatumPort;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class MarketDatumRepository implements CreateMarketDatumPort {

    private final MybatisMarketDatumRepository marketDatumRepository;
    private final MybatisMarketDatumEntityMapper marketDatumEntityMapper;

    @Override
    public void insertWhenNotExists(@NotBlank @NotNull UUID stationId, @NotNull MarketDatum marketDatum) {
        marketDatumRepository.insertWhenNotExists(stationId, marketDatumEntityMapper.map(marketDatum));
    }
}
