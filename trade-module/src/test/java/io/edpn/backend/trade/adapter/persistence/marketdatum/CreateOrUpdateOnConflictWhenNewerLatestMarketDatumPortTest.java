package io.edpn.backend.trade.adapter.persistence.marketdatum;

import io.edpn.backend.trade.adapter.persistence.LatestMarketDatumRepository;
import io.edpn.backend.trade.adapter.persistence.entity.MarketDatumEntity;
import io.edpn.backend.trade.adapter.persistence.entity.mapper.MarketDatumEntityMapper;
import io.edpn.backend.trade.adapter.persistence.repository.MybatisLatestMarketDatumRepository;
import io.edpn.backend.trade.application.domain.MarketDatum;
import io.edpn.backend.trade.application.port.outgoing.marketdatum.createOrUpdateExistingWhenNewerLatestMarketDatumPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateOrUpdateOnConflictWhenNewerLatestMarketDatumPortTest {

    @Mock
    private MybatisLatestMarketDatumRepository mybatisLatestMarketDatumRepository;

    @Mock
    private MarketDatumEntityMapper marketDatumEntityMapper;

    private createOrUpdateExistingWhenNewerLatestMarketDatumPort underTest;

    @BeforeEach
    public void setUp() {
        underTest = new LatestMarketDatumRepository(mybatisLatestMarketDatumRepository, marketDatumEntityMapper);
    }

    @Test
    void testCreateWhenNotExists() {
        UUID uuid = UUID.randomUUID();
        MarketDatum inputMarketDatum = mock(MarketDatum.class);
        MarketDatumEntity inputMarketDatumEntity = mock(MarketDatumEntity.class);

        when(marketDatumEntityMapper.map(inputMarketDatum)).thenReturn(inputMarketDatumEntity);

        underTest.createOrUpdateWhenNewer(uuid, inputMarketDatum);

        verify(marketDatumEntityMapper, times(1)).map(inputMarketDatum);
        verify(mybatisLatestMarketDatumRepository, times(1)).createOrUpdateExistingWhenNewer(uuid, inputMarketDatumEntity);
    }
}
