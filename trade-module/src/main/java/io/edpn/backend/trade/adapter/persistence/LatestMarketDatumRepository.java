package io.edpn.backend.trade.adapter.persistence;

import io.edpn.backend.trade.adapter.persistence.entity.mapper.MybatisMarketDatumEntityMapper;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisLatestMarketDatumRepository;
import io.edpn.backend.trade.application.domain.MarketDatum;
import io.edpn.backend.trade.application.port.outgoing.marketdatum.CreateLatestMarketDatumPort;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class LatestMarketDatumRepository implements CreateLatestMarketDatumPort {

    private final MybatisLatestMarketDatumRepository mybatisLatestMarketDatumRepository;
    private final MybatisMarketDatumEntityMapper marketDatumEntityMapper;

    @Override
    public void insertOrUpdate(@NotBlank @NotNull UUID stationId, @NotNull MarketDatum marketDatum) {
        mybatisLatestMarketDatumRepository.insertOrUpdate(stationId, marketDatumEntityMapper.map(marketDatum));
    }
}
