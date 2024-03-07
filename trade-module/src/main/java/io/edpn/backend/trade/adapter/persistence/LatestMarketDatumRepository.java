package io.edpn.backend.trade.adapter.persistence;

import io.edpn.backend.trade.adapter.persistence.entity.mapper.MybatisMarketDatumEntityMapper;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisLatestMarketDatumRepository;
import io.edpn.backend.trade.application.domain.MarketDatum;
import io.edpn.backend.trade.application.port.outgoing.marketdatum.createOrUpdateExistingWhenNewerLatestMarketDatumPort;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class LatestMarketDatumRepository implements createOrUpdateExistingWhenNewerLatestMarketDatumPort {

    private final MybatisLatestMarketDatumRepository mybatisLatestMarketDatumRepository;
    private final MybatisMarketDatumEntityMapper mybatisMarketDatumEntityMapper;

    @Override
    public void createOrUpdateWhenNewer(@NotBlank @NotNull UUID stationId, @NotNull MarketDatum marketDatum) {
        mybatisLatestMarketDatumRepository.createOrUpdateExistingWhenNewer(stationId, mybatisMarketDatumEntityMapper.map(marketDatum));
    }
}
