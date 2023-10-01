package io.edpn.backend.trade.adapter.persistence;

import io.edpn.backend.trade.adapter.persistence.entity.mapper.MybatisMarketDatumEntityMapper;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisLatestMarketDatumRepository;
import io.edpn.backend.trade.application.domain.MarketDatum;
import io.edpn.backend.trade.application.port.outgoing.marketdatum.CreateOrUpdateOnConflictWhenNewerLatestMarketDatumPort;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class LatestMarketDatumRepository implements CreateOrUpdateOnConflictWhenNewerLatestMarketDatumPort {

    private final MybatisLatestMarketDatumRepository mybatisLatestMarketDatumRepository;
    private final MybatisMarketDatumEntityMapper marketDatumEntityMapper;

    @Override
    public void createOrUpdateWhenNewer(@NotBlank @NotNull UUID stationId, @NotNull MarketDatum marketDatum) {
        mybatisLatestMarketDatumRepository.createOrUpdateOnConflictWhenNewer(stationId, marketDatumEntityMapper.map(marketDatum));
    }
}
